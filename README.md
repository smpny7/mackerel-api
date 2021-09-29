## Javaの動作について

Java16環境にて動作確認済みです。


## アプリケーションの実行について

```shell
./gradlew bootRun
```

## 環境変数の設定

Firebase の接続情報のファイルパスを、環境変数 `GOOGLE_APPLICATION_CREDENTIALS` に設定する必要があります。

Macの場合、`.zshrc`ファイルに以下を追記し、シェルを再起動することでアプリケーションを認証し、通信が行えます。
```shell
export GOOGLE_APPLICATION_CREDENTIALS="/Users/coalamode/mackerel-app-firebase-adminsdk-10inj-97c9f3bb0c.json"
```

シェルの再起動は、以下のコマンドで行えます。
```shell
source ~/.zshrc
```