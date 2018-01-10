
#  PhotosDemo【调用系统相机和相册】

     关于Android7.0调用系统相机拍照、访问相册的一些问题：

     在Android6.0中Google提出了动态申请权限的Api,调用相机拍照，访问SDcard等操作都需要先申请对应的权限如下：

      <uses-permission android:name="android.permission.CAMERA" />

      <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />


      Google是反对放宽私有目录的访问权限的，所以收起对私有文件的访问权限是Android将来发展的趋势。
      Android7.0中尝试传递 file:// URI 会触发 FileUriExposedException，因为在Android7.0之后Google认为直接使用
      本地的根目录即file:// URI是不安全的操作，直接访问会抛出FileUriExposedExCeption异常，这就意味着在Android7.0
      以前我们访问相机拍照存储时，如果使用URI的方式直接存储剪裁图片就会造成这个异常，那么如何解决这个问题呢？

     

      方法一：

        public class IApplocation extends Application {

                @Override

                public void onCreate() {

                    super.onCreate();

                    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();

                    StrictMode.setVmPolicy(builder.build());

                    builder.detectFileUriExposure();

                }

        }

        注：方法一在Application忽略后，在调用相机和相册时方法二注意事项可以忽略不用添加，比较简洁，目前也还没有发现
        其它问题，建议使用第二种方法。




      方法二：
      
      Google为我们提供了FileProvider类，进行一种特殊的内容提供，FileProvider时ContentProvide的子类，它使用了和内容
      提供器类似的机制来对数据进行保护，可以选择性地将封装过的Uri共享给外部，从而提高了应用的安全性。下面就让我们看一下
      如何使用这个内容提供者进行数据访问的：
      使用FileProvider获取Uri就会将以前的file:// URI准换成content:// URI，实现一种安全的应用间数据访问，内容提供者
      作为Android的四大组件之一，

      1.使用同样需要在清单文件AndroidManifest.xml中进行注册的，注册方法如下：


    <provider

       android:name="android.support.v4.content.FileProvider"

       android:authorities="cn.waves.fileprovider"

       android:exported="false"

       android:grantUriPermissions="true">

    <meta-data

        android:name="android.support.FILE_PROVIDER_PATHS"

        android:resource="@xml/file_paths" />

    </provider>



     2.provider标签里的 android:name的值是FileProvider的包名+类名为固定值。android:authorities的值相当于一个标志，
     当我们使用FileProvider的getUriForFile方法时的一个参数需和清单文件注册时的保持一致，
     这里我使用的是:cn.waves.fileprovider可自行定义。exported:要求必须为false，为true则会报安全异常。
     grantUriPermissions:true，表示授予 URI 临时访问权限。
     <meta-data />标签里面是用来指定共享的路径。 android:resource="@xml/file_paths"就是我们的共享路径配置的xml文件

      在res目录下创建xml文件夹，file_paths.xml文件内容如下：

     <?xml version="1.0" encoding="utf-8"?>

     <resources>

        <paths>

            <external-path

               name="camera_photos"

               path="" />

        </paths>

     </resources>


     external-path标签用来指定Uri共享的，name属性的值可以自定义，path属性的值表示共享的具体位置，设置为空，
     就表示共享整个SD卡，也可指定对应的SDcard下的文件目录，根据需求自行定义。接下来就是调用系统相机进行拍照了,
     大家可以查看提供的代码。

      注：PhotoUtils是对拍照和相册获取照片的封装
      
      注：PhoneSystemUtils是对拍照未赋予权限情况针对手机动态提示权限申请
      
      注：本文Demo为大家提供一个参考，两种方法在代码中都有体现
      


      分享此demo也是在工作中遇到这样的问题，自己也是通过查看一些大神的分享，在这里我把这些问题总结到个人页，也是方便查看，同时也希望可以帮助到需要的人。谢谢！
      
      在此也为大家推广一个比较好的框架，一个开发App秒载数据支持http和https请求，以及框架支持其它一些功能。
     ` 链接地址：https://github.com/LeWaves/App-FrameWork`

      联系人：Waves
      E-mail: LeWaves@yeah.net
