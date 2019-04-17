# Python学习

## 一、Python基础

Python的语法比较简单，采用缩进方式：

```python
#!/usr/bin/env python3
# This is Python code
a = input("Please enter youer number: ")
try:
    b = int(a)
    if b >= 10:
        print("Your number is bigger than 10.")
	else:
        print("Your number is smaller than 10.")
except:
    print("Please input integer number.")
```

+ 以`#`开头的语句是注释，解释器会忽略掉注释。

+ 其他每一行都是一个语句，当语句以冒号`:`结尾时，缩进的语句视为代码块（应该始终坚持使用**4个空格**的缩进）。

+ Python程序是**大小写敏感**的，如果写错了大小写，程序会报错。



### 1、数据类型

