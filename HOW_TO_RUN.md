# IntelliJ IDEAを使う場合

* IntelliJ IDEAの導入
  * https://www.jetbrains.com/idea/download/
  * OSによってリンク先の指示に従ってインストールする
* 実行する
  * ツールバー右側の「Maven]を選択
    * nub2→testを実行

# 端末を使う場合

```sh
$ ./mvnw compile # コンパイル

$ ./mvnw package # fat jarの作成
```

> 初回実行時に[Maven Wrapper](https://github.com/takari/maven-wrapper)によりMaven 3のダウンロードが行われます。
