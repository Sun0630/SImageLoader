## 自己动手实现图片加载框架SImageLoader

--- 
### 以实现功能
* 图片压缩
* [Bitmap的加载和缓存策略](https://sun0630.github.io/2017/09/01/Bitmap%E7%9A%84%E5%8A%A0%E8%BD%BD%E5%92%8CCache/)
* 从网络获取图片
* 异步加载图片（使用线程池）

```
mSImageLoader.bindBitmap(url, mImageView, mImageWidth, mImageWidth);
```

### 实现图片墙效果
* 优化列表卡顿现象（滑动的时候停止加载图片）

<img src="1.jpg" width='400'/>

