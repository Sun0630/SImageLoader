package com.sx.simageloader;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.sx.simageloader.imageloader.SImageLoader;
import com.sx.simageloader.utils.Utils;
import com.sx.simageloader.view.RecyclerViewItemDecoration;
import com.sx.simageloader.view.SquareImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<String> mUrList = new ArrayList<String>();
    private int mImageWidth = 0;
    private boolean mIsWifi = false;
    private boolean mCanGetBitmapFromNetWork = false;
    private SImageLoader mSImageLoader;
    private ImageAdapter mImageAdapter;

    private boolean isRVIdle = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();

    }

    private void initView() {
        mSImageLoader = SImageLoader.build(this);
        if (!mIsWifi) {
            //提醒用户注意流量
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder
                    .setMessage("您正在使用数据流量进行访问，要继续吗？")
                    .setTitle("提示")
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mCanGetBitmapFromNetWork = true;
                            mImageAdapter.notifyDataSetChanged();
                        }
                    })
                    .setNegativeButton("取消", null)
                    .show();
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mImageAdapter = new ImageAdapter();
        mRecyclerView.setAdapter(mImageAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    isRVIdle = true;
                    mImageAdapter.notifyDataSetChanged();
                }else {
                    isRVIdle = false;
                }
            }
        });
        mRecyclerView.addItemDecoration(new RecyclerViewItemDecoration.Builder(this)
                .setOrientation(RecyclerViewItemDecoration.Vertical | RecyclerViewItemDecoration.Horizontal)
                .setDividerHeight(2)
                .setDividerWidth(2)
                .setDividerColor(Color.parseColor("#FFFFFF"))
                .build());
    }

    private void initData() {
        String[] imageUrls = {
                "https://ws1.sinaimg.cn/large/610dc034ly1fiz4ar9pq8j20u010xtbk.jpg",
                "https://ws1.sinaimg.cn/large/610dc034ly1fiuiw5givwj20u011h79a.jpg",
                "http://pic41.nipic.com/20140518/4135003_102912523000_2.jpg",
                "http://img2.imgtn.bdimg.com/it/u=1133260524,1171054226&fm=21&gp=0.jpg",
                "http://h.hiphotos.baidu.com/image/pic/item/3b87e950352ac65c0f1f6e9efff2b21192138ac0.jpg",
                "http://pic42.nipic.com/20140618/9448607_210533564001_2.jpg",
                "http://pic10.nipic.com/20101027/3578782_201643041706_2.jpg",
                "http://img2.3lian.com/2014/c7/51/d/26.jpg",
                "http://img3.3lian.com/2013/c1/34/d/93.jpg",
                "http://b.zol-img.com.cn/desk/bizhi/image/3/960x600/1375841395686.jpg",
                "http://cdn.duitang.com/uploads/item/201311/03/20131103171224_rr2aL.jpeg",
                "http://imgrt.pconline.com.cn/images/upload/upc/tx/wallpaper/1210/17/c1/spcgroup/14468225_1350443478079_1680x1050.jpg",
                "http://pic41.nipic.com/20140518/4135003_102025858000_2.jpg",
                "http://www.1tong.com/uploads/wallpaper/landscapes/200-4-730x456.jpg",
                "http://pic.58pic.com/58pic/13/00/22/32M58PICV6U.jpg",
                "http://h.hiphotos.baidu.com/zhidao/wh%3D450%2C600/sign=429e7b1b92ef76c6d087f32fa826d1cc/7acb0a46f21fbe09cc206a2e69600c338744ad8a.jpg",
                "http://pica.nipic.com/2007-12-21/2007122115114908_2.jpg",
                "http://cdn.duitang.com/uploads/item/201405/13/20140513212305_XcKLG.jpeg",
                "http://photo.loveyd.com/uploads/allimg/080618/1110324.jpg",
                "http://img4.duitang.com/uploads/item/201404/17/20140417105820_GuEHe.thumb.700_0.jpeg",
                "http://cdn.duitang.com/uploads/item/201204/21/20120421155228_i52eX.thumb.600_0.jpeg",
                "http://img4.duitang.com/uploads/item/201404/17/20140417105856_LTayu.thumb.700_0.jpeg",
                "http://img04.tooopen.com/images/20130723/tooopen_20530699.jpg",
                "http://pic.dbw.cn/0/01/33/59/1335968_847719.jpg",
                "http://a.hiphotos.baidu.com/image/pic/item/a8773912b31bb051a862339c337adab44bede0c4.jpg",
                "http://h.hiphotos.baidu.com/image/pic/item/f11f3a292df5e0feeea8a30f5e6034a85edf720f.jpg",
                "http://img0.pconline.com.cn/pconline/bizi/desktop/1412/ER2.jpg",
                "http://pic.58pic.com/58pic/11/25/04/91v58PIC6Xy.jpg",
                "http://img3.3lian.com/2013/c2/32/d/101.jpg",
                "http://pic25.nipic.com/20121210/7447430_172514301000_2.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1504284147171&di=040527739d4caaed9794d2902f49d5e4&imgtype=0&src=http%3A%2F%2Fwww.sinaimg.cn%2Fdy%2Fslidenews%2F2_img%2F2012_47%2F730_854951_194926.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1504284147171&di=33530945a6f1da22933ecdcd5baee60f&imgtype=0&src=http%3A%2F%2Fwww.sinaimg.cn%2Fdy%2Fslidenews%2F2_img%2F2012_47%2F730_854929_674039.jpg",
                "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1558983090,2134523666&fm=11&gp=0.jpg",
                "http://img02.tooopen.com/images/20140320/sy_57121781945.jpg",
                "http://www.renyugang.cn/emlog/content/plugins/kl_album/upload/201004/852706aad6df6cd839f1211c358f2812201004120651068641.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1504284234749&di=4bc4129fe9546e1ca4b50175e6796ab4&imgtype=jpg&src=http%3A%2F%2Fb.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F314e251f95cad1c8cecf8ee7763e6709c83d51fd.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1504284241672&di=bcd9381d212c254eddbbe56c956dfa39&imgtype=jpg&src=http%3A%2F%2Fc.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F6c224f4a20a44623fbe8d0a39122720e0df3d7d5.jpg",
                "https://ws1.sinaimg.cn/large/610dc034ly1fj2ld81qvoj20u00xm0y0.jpg",
                "https://ws1.sinaimg.cn/large/610dc034ly1fj3w0emfcbj20u011iabm.jpg",
        };
        for (String url : imageUrls) {
            mUrList.add(url);
        }
        int screenWidth = Utils.getScreenMetrics(this).widthPixels;
        int space = (int) Utils.dp2px(this, 20f);
        mImageWidth = (screenWidth - space) / 3;
        mIsWifi = Utils.isWifi(this);
        if (mIsWifi) {
            mCanGetBitmapFromNetWork = true;
        }
    }

    public class ImageAdapter extends RecyclerView.Adapter {


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = View.inflate(MainActivity.this, R.layout.item, null);
            ImageHolder imageHolder = new ImageHolder(view);
            return imageHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ImageHolder imageHolder = (ImageHolder) holder;
            imageHolder.setData(position);
        }

        @Override
        public int getItemCount() {
            return mUrList.size();
        }

        private class ImageHolder extends RecyclerView.ViewHolder {
            private SquareImageView mImageView;

            public ImageHolder(View itemView) {
                super(itemView);
                mImageView = (SquareImageView) itemView.findViewById(R.id.siv);
            }

            public void setData(int position) {
                String url = mUrList.get(position);
                String tag = (String) mImageView.getTag();
                if (!url.equals(tag)) {
                    mImageView.setImageResource(R.mipmap.image_default);
                }
                //滑动的时候不加载数据
                if (mCanGetBitmapFromNetWork && isRVIdle) {
                    mImageView.setTag(url);
                    mSImageLoader.bindBitmap(url, mImageView, mImageWidth, mImageWidth);
                }
            }
        }


    }
}
