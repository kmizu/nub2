# Nub2: A Tiny Programming Language

[![Build Status](https://travis-ci.org/kmizu/nub2.png?branch=master)](https://travis-ci.org/kmizu/nub2)

このレポジトリは[プログラミング言語作成ハンズオン](https://connpass.com/event/144535/)向けのレポジトリです。
Nubとは英語で**小さな塊や隆起**を意味する言葉です。Nub-v2のインタプリタはJavaを使って書かれています。

* `src/main/java/...`: Javaソースコード
* `pom.xml`: Maven用コンフィグ

> Nub2のコンパイルにはMaven 3を利用していますが、[Maven Wrapper](https://github.com/takari/maven-wrapper)を含んでいるので別途インストールする必要はありません。

## 開発者向けドキュメント

[How To Run](HOW_TO_RUN.md)

## 整数リテラル

```java
1
5
10
100
1000
...
```

## 四則演算式

```java
1 + 2
1 + 2 * 3
(1 + 2) * 3
(1 + 2) * 3 / 4
```

## 値宣言・代入・呼出

各プログラムの行の最後にはセミコロンが必要です。

```java
let x = 1;
let y = 2;
print(x + y); //3
x = 2;
print(x + y); //4
// let x = 1; is not allowed since x is already defined
```

## 比較演算式

```java
1 < 2
3 < 4
3 <= 3
6 > 5
7 > 5
7 >= 7
1 == 1
5 == 5
1 != 2
3 != 2
```

## プリント

### 文法

```java
print(expression);
```

初期は整数値の表示のみ対応しています。
将来的に整数値以外にも対応する予定です。

## 条件式

### 文法

```
if (condition) {
  expression
  ...
} (else {
  expression
  ...
})?
```

### 例

```
if(1 < 2) {
  print(1);
}else {
  print(2);
}
```

## ループ式

### 文法

```
while(condition) {
  expression
  ...
}
```

### 例

```
let i = 0;
while(i < 10) {
  print(i);
  i = i + 1;
}
```

値宣言には`let`を使います。代入演算もサポートされています。
