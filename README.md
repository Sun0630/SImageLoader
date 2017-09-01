## 自己动手实现图片加载框架SImageLoader

--- 
### 以实现功能
* 图片压缩
* 图片三级缓存（LruCache与DiskLruCache）
* 从网络获取图片
* 异步加载图片（使用线程池）

```
mSImageLoader.bindBitmap(url, mImageView, mImageWidth, mImageWidth);
```

### 实现图片墙效果
* 优化列表卡顿现象（滑动的时候停止加载图片）

<img src="1.jpg"/>

