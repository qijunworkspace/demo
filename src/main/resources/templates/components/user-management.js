var UserManagement = function(){

    //用户状态
    var userStatus = {
        0: {desc: "待审核", class: "m-badge--brand"},
        1: {desc: "正常", class: " m-badge--info"},
        2: {desc: "已锁定", class: " m-badge--warning"},
        3: {desc: "已删除", class: " m-badge--danger"},
    };

    //表格对象
    var table;

    var roles = {};


    var handleBootstrapSelect = function(){
        //加载现有角色
        $.ajax({
            url: "/user/getRoleList",
            type: 'POST',
            dataType: "json",
            success: function(result) {
                if (200 == result.code){
                    $.each(result.data, function(i, n){
                        roles[n.id] = n.role;
                        $('#searchRole').append('<option value="'+n.id+'">'+n.role+'</option>');
                        $('#reviewRole').append('<option value="'+n.id+'">'+n.role+'</option>');
                        $('#modifyRole').append('<option value="'+n.id+'">'+n.role+'</option>');
                        $('#addRole').append('<option value="'+n.id+'">'+n.role+'</option>');
                    });
                }
                $(".m_selectpicker").selectpicker();
            },
            error: function(XMLHttpRequest, textStatus, errorThrown){
                swal("Something went wrong", errorThrown, "error")
            }
        });


    };

    var searchUserTable = function(){

        //table.ajax.reload();
        //$('#alarmList').dataTable().fnDraw();
        table = $("#userTable").DataTable({
            /*language: {
                "processing":   "处理中...",
                "lengthMenu":   "_MENU_ 记录/页",
                "zeroRecords":  "没有匹配的记录",
                "info":         "第 _START_ 至 _END_ 项记录，共 _TOTAL_ 项",
                "infoEmpty":    "第 0 至 0 项记录，共 0 项",
                "infoFiltered": "(由 _MAX_ 项记录过滤)",
                "infoPostFix":  "",
                "search":       "搜索:",
                "url":          "",
                "decimal": ",",
                "thousands": ".",
                "emptyTable":"未找到符合条件的数据",
                "paginate": {
                    "first":    "首页",
                    "previous": "上页",
                    "next":     "下页",
                    "last":     "末页"
                }
            },*/
            responsive: true,
            //清空原有表格
            "destroy": true,
            //保留排序和分页状态, 和Table.fnDraw(false);结合使用
            stateSave: true,
            //检索
            retrieve: true,
            //datatables默认搜索
            searching: false,
            //分页
            paging: true,
            //排序
            ordering: false,
            //提示信息
            info: true,
            //自动宽度
            autoWidth: false,
            //每页显示10条数据
            pageLength: 10,
            //分页样式：simple,simple_numbers,full,full_numbers，
            pagingType: "full_numbers",
            //去掉搜索框方法
            bFilter: false,
            //页面上确认是否可以进行选择一页展示多少条
            bLengthChange: false,
            //启用服务器端分页
            serverSide: true,
            ajax : function(data, callback, settings) {
                var param = {};
                param.draw = data.draw;
                param.pageIndex = (data.start) / data.length + 1;
                param.pageSize = data.length;
                param.searchName = $("#searchName").val();
                param.searchRole = $("#searchRole").val();
                $.ajax({
                    url: "/user/searchUser",
                    type: 'POST',
                    dataType: "json",
                    data: param,
                    success: function(result) {
                        if (result.code == 200){
                            //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                            //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                            callback(result.data);
                        }
                    },
                    error: function(XMLHttpRequest, textStatus, errorThrown){
                        swal("Something went wrong", errorThrown, "error")
                    }
                });

            },
            columns: [
                //隐藏主键
                {"data": "id",  "defaultContent": "", "visible": false},
                {"data": "username", "defaultContent": ""},
                {"data": null},
                {"data": null},
                {"data": "telephone", "defaultContent": ""},
                {"data": "email", "defaultContent": ""},
                {"data": "registTime", "defaultContent": ""},
                {"data": null},
            ],
            columnDefs: [
                {
                    targets: 2, title: "Role", orderable: false, render: function (data, type, row, meta) {
                        return void 0 === data.authorities[0] ? "" : data.authorities[0].authority;
                    }
                },
                {
                    targets: 3, title: "Status", orderable: false, render: function (data, type, row, meta) {
                        return  void 0 === userStatus[data.status] ? data.status : '<span class="m-badge ' + userStatus[data.status].class + ' m-badge--wide">' + userStatus[data.status].desc + "</span>"
                    }
                },
                {
                    targets: -1, title: "Actions", orderable: false, render: function (data, type, row, meta) {
                        var content = "";
                        if (0 == data.status) {
                            //待审核
                            content = '<a onclick="UserManagement.loadUserReview(\''+data.id+'\')" class="m-portlet__nav-link btn m-btn m-btn--hover-brand m-btn--icon m-btn--icon-only m-btn--pill" title="Review">\n' +
                                '   <i class="flaticon-avatar"></i>\n ' +
                                '</a>';
                        }else{
                            //其他状态可更改
                            content = '<a onclick="UserManagement.loadUserModify(\''+data.id+'\')" data-toggle="modal" data-target="#modifyModal"  class="m-portlet__nav-link btn m-btn m-btn--hover-brand m-btn--icon m-btn--icon-only m-btn--pill" title="Modify">\n' +
                                '   <i class="flaticon-settings-1"></i>\n ' +
                                '</a>';
                        }
                        return content;
                     }
                },
            ]
        })
    };

    var handleSearchBtn = function(){
        $("#searchBtn").click(function(e){
            e.preventDefault();
            //添加禁止点击
            $(this).addClass('m-loader m-loader--right m-loader--light').attr('disabled', true);
            table.ajax.reload();
            $(this).removeClass('m-loader m-loader--right m-loader--light').attr('disabled', false);
        });
    }

    var loadUserReview = function(id){
        var form = $("#reviewModal").find("form");
        //清空原始值
        form.resetForm();
        //清空验证结果
        form.validate().resetForm();
        //加载当前点击用户
        $.ajax({
            url: "/user/getUserInfo",
            type: 'POST',
            dataType: "json",
            data: {userId: id},
            success: function(result) {
                if (null == result.data){
                    swal("USER NOT FOUND", "Please refresh page", "error");
                    return;
                }
                if (result.code == 200){
                    $('#reviewModal').find("[name=userId]").val(result.data.id);
                    if (null != result.data.role){
                        $('#reviewRole').selectpicker('val', result.data.role.id);
                    }else{
                        $('#reviewRole').selectpicker('val', "");
                    }
                    $("#reviewStatus").selectpicker('val', result.data.status);

                    $("#reviewModal").modal('show');
                }
            },
            error: function(XMLHttpRequest, textStatus, errorThrown){
            }
        });
    }

    var handleReviewBtn = function(){
        $("#reviewBtn").click(function(e){
            e.preventDefault();
            //验证form
            var form = $("#reviewModal").find("form");
            var btn = $(this);

            form.validate({
                rules: {
                    roleId: {
                        required: true,
                    },
                    status: {
                        required: true,
                    }
                },
                messages: {}
            });
            if (!form.valid()) {
                return;
            }

            //添加禁止点击
            btn.addClass('m-loader m-loader--right m-loader--light').attr('disabled', true);
            //提交更改
            form.ajaxSubmit({
                url: '/user/reviewUser',
                type: 'post',
                data: form.serialize(),
                success: function(response) {

                    if (200 == response.code){
                        swal("OK",response.message,"success");
                        table.ajax.reload();
                        $("#reviewModal").modal('hide');
                    }
                    btn.removeClass('m-loader m-loader--right m-loader--light').attr('disabled', false);
                },
                error: function (data) {
                    $("#reviewModal").modal('hide');
                    btn.removeClass('m-loader m-loader--right m-loader--light').attr('disabled', false);
                }

            });

        });
    }

    var loadUserModify = function(id){
        var form = $("#modifyModal").find("form");
        //清空原始值
        form.resetForm();
        //清空验证结果
        form.validate().resetForm();
        //加载当前点击用户
        $.ajax({
            url: "/user/getUserInfo",
            type: 'POST',
            dataType: "json",
            data: {userId: id},
            success: function(result) {
                if (null == result.data){
                    swal("USER NOT FOUND", "Please refresh page", "error");
                    return;
                }
                if (result.code == 200){
                    $('#modifyModal').find("[name=userId]").val(result.data.id);
                    $('#modifyModal').find("[name=username]").val(result.data.username);
                    $('#modifyModal').find("[name=password]").val(result.data.password);
                    if (null != result.data.role){
                        $('#modifyRole').selectpicker('val', result.data.role.id);
                    }else{
                        $('#modifyRole').selectpicker('val', "");
                    }
                    $("#modifyStatus").selectpicker('val', result.data.status);

                    $("#modifyModal").modal('show');
                }
            },
            error: function(XMLHttpRequest, textStatus, errorThrown){
            }
        });
    }

    var handleModifyBtn = function(){
        $("#modifyBtn").click(function(e){
            e.preventDefault();
            //验证form
            var form = $("#modifyModal").find("form");
            var btn = $(this);

            form.validate({
                rules: {
                    password:{
                        required: true,
                        minlength: 5,
                        maxlength: 20,
                        normalizer: function(value) {
                            return $.trim(value);
                        }
                    },
                    roleId: {
                        required: true,
                    },
                    status: {
                        required: true,
                    }
                },
                messages: {}
            });
            if (!form.valid()) {
                return;
            }

            //添加禁止点击
            btn.addClass('m-loader m-loader--right m-loader--light').attr('disabled', true);
            //提交更改
            form.ajaxSubmit({
                url: '/user/modifyUser',
                type: 'post',
                data: form.serialize(),
                success: function(response) {

                    if (200 == response.code){
                        swal("OK",response.message,"success");
                        table.ajax.reload();
                        $("#modifyModal").modal('hide');
                    }
                    btn.removeClass('m-loader m-loader--right m-loader--light').attr('disabled', false);
                },
                error: function (data) {
                    $("#modifyModal").modal('hide');
                    btn.removeClass('m-loader m-loader--right m-loader--light').attr('disabled', false);
                }

            });

        });
    }

    var handleAddUserToggle = function(){

        $("#addUserBtn").click(function(e){
            e.preventDefault();
            $("#addUser").find("form").resetForm();
            $(".toggle-content").toggleClass("m--hide");
        });
        $("#addCancel").click(function(e){
            e.preventDefault();
            $("#addUser").find("form").resetForm();
            $(".toggle-content").toggleClass("m--hide");
        });
    }


    var handleAddUserSubmit = function () {
        $("#addSubmit").click(function(e){
            e.preventDefault();
            var btn = $(this);
            var form = $(this).closest('form');

            form.validate({
                rules: {
                    username: {
                        required: true,
                        minlength: 5,
                        maxlength: 20,
                    },
                    email: {
                        required: true,
                        email: true
                    },
                    password: {
                        required: true,
                        minlength: 5,
                        maxlength: 20,
                    },
                    rpassword: {
                        required: true,
                        //需要设置ID来查找
                        equalTo: "#newPassword"
                    },
                    roleId: {
                        required: true
                    }
                }
            });

            if (!form.valid()) {
                return;
            }

            btn.addClass('m-loader m-loader--right m-loader--light').attr('disabled', true);
            //提交注册
            form.ajaxSubmit({
                url: '/user/addUser',
                type: 'post',
                dataType: 'json',
                //beforeSerialize:function(){} //序列化提交数据之前的回调函数
                //beforeSubmit:function(){},　　//提交前执行的回调函数
                //clearForm:true,　　　　　　 //提交成功后是否清空表单中的字段值
                //restForm:true,　　　　　　  //提交成功后是否重置表单中的字段值，即恢复到页面加载时的状态
                success: function(response) {
                    //判断后台操作是否成功
                    if (200 == response.code){
                        // 提示成功信息
                        swal({
                            text: response.message,
                            type: 'success',
                            showConfirmButton: true,
                            confirmButtonText: "Back"
                        }).then(function(result) {
                            if (result.value) {
                                table.ajax.reload();
                                form.resetForm();
                                form.validate().resetForm();
                            }
                        });
                    }
                    btn.removeClass('m-loader m-loader--right m-loader--light').attr('disabled', false);
                },
                error: function(data){
                    console.log(data)
                    btn.removeClass('m-loader m-loader--right m-loader--light').attr('disabled', false);
                }
            });
        });
    }


    //== Public Functions
    return {
        // public functions
        init: function() {
            handleBootstrapSelect();
            searchUserTable();
            handleSearchBtn();
            handleReviewBtn();
            handleModifyBtn();
            handleAddUserToggle();
            handleAddUserSubmit();
        },
        loadUserReview: loadUserReview,
        loadUserModify: loadUserModify
    };
}();



jQuery(document).ready(function(){
    UserManagement.init();
});