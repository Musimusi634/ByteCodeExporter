# ByteCode Exporter
実行中のminecraftからクラスのバイトコードを取得して保存するコマンドを追加します。
MixinやASMのデバッグ向けのmodです。
使用するにはJVM引数に`-Djdk.attach.allowAttachSelf=true`を追加して自己アタッチを許可する必要があります。
もしくは、[ForbiddenThings](https://github.com/kosianodanngoo/ForbiddenThings/tree/master)や[NoSugar](https://github.com/consome-c11/no-Sugar)のような自己アタッチを許可する機能を持つmodを使用することもできます。

## 使用方法
```
/exportbytecode [ClassNath]
```
を実行します。[ClassNath]は完全修飾クラス名である必要があります。権限レベル3が必要です。
例:
```
/exportbytecode net.minecraft.world.entity.LivingEntity
```
`Bytecode Exported: [Filename]`というメッセージが表示されれば成功です。
取得されたバイトコードはminecraft/ByteCodeExporterに.classファイルとして保存されます。

使用する際はライセンスに十分注意してください。