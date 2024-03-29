package com.zzci.light;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.hardware.SensorManager;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ColorLightActivity extends Activity {
	private boolean isopent = false;
	private Camera camera;
	private LinearLayout mylayout;
	private Resources myColor;
	private int li;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HideStatusBase();
        setContentView(R.layout.main);
        
        //得到layout背景
        mylayout=(LinearLayout)findViewById(R.id.mylayout);
       // 改变布局的背景颜色
        SetColor(R.color.white);
        //结束改变背景颜色
        //改变屏幕亮度
        li=0;
        SetBright(1.0f);
        
    }
  
   
    /**
     * 屏幕点击事件显示菜单
     */
    @Override
    public boolean onTouchEvent(MotionEvent event){
    	//点击屏幕的时候 弹出菜单
    	openOptionsMenu();
    	return false;
    }
    /**
     * 关联菜单
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
    	super.onCreateOptionsMenu(menu);
    	getMenuInflater().inflate(R.menu.menu, menu);
    	//menu.findItem(R.id.about).setEnabled(false);
    	return true;
    }
    
    /**
     * 捕捉菜单事件
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    switch(item.getItemId())
    {
    case R.id.about:
    	//Toast.makeText(ColorLightActivity.this, "关于菜单", Toast.LENGTH_LONG).show();
    	about();
    	return true;
    case R.id.setcolor:
    	//Toast.makeText(ColorLightActivity.this, R.string.setcolor, Toast.LENGTH_SHORT).show();
    	selectColor();
    	return true;
    case R.id.setbright:
    	selectBright();
    	//Toast.makeText(ColorLightActivity.this, "关于菜单", Toast.LENGTH_LONG).show();
    	return true;
    case R.id.seteffer:
    	//Toast.makeText(ColorLightActivity.this, "关于菜单", Toast.LENGTH_LONG).show();
    	finish();
    	return true;
    case R.id.flash:
    	flashlight();
    }
    return false;
    }
    
    /**
     * 点亮后置摄像头方法
     */
    public void flashlight(){
    	
    	if (!isopent) {
			Toast.makeText(getApplicationContext(), "您已经打开了手电筒", 0)
					.show();
			camera = Camera.open();
			Parameters params = camera.getParameters();
			params.setFlashMode(Parameters.FLASH_MODE_TORCH);
			camera.setParameters(params);
			camera.startPreview(); // 开始亮灯

			isopent = true;

		} else {
			Toast.makeText(getApplicationContext(), "关闭了手电筒",
					Toast.LENGTH_SHORT).show();
			camera.stopPreview();
			camera.release();
			isopent = false;

		}
    }
    /**
     * 选择颜色
     */
    public void selectColor()
    {
    	final String[] items = {"白色", "红色", "黑色","黄色","粉色"}; 
    	new AlertDialog.Builder(this) 
    	.setTitle("选择颜色") //此处 this 代表当前Activity 
    	.setItems(items, new DialogInterface.OnClickListener() { 
    	public void onClick(DialogInterface dialog, int item) { 
    	Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show(); //将选中的文本内容按照土司提示 方式显示出来, 此处的getApplicationContext() 得到的也是当前的Activity对象，可用当前Activity对象的名字.this代替（Activity.this） 
    	switch (item) {
		case 0:
			SetColor(R.color.white);
			break;
		case 1:
			SetColor(R.color.red);
			break;
		case 2:
			SetColor(R.color.black);
			break;
		case 3:
			SetColor(R.color.yellow);
			break;
		case 4:
			SetColor(R.color.fs);
			break;
		default:
			SetColor(R.color.white);
			break;
		}
    	} 
    	}).show();//显示对话框 
    }
    
    /**
     * 选择亮度
     */
    public void selectBright()
    {
    	final String[] items = {"100%", "75%", "50%","25%","10%"}; 
    	new AlertDialog.Builder(this) 
    	.setTitle("选择亮度") 
    	.setSingleChoiceItems(items, li, new DialogInterface.OnClickListener() { //此处数字为选项的下标，从0开始， 表示默认哪项被选中 
    	public void onClick(DialogInterface dialog, int item) { 
    	Toast.makeText(getApplicationContext(), items[item],Toast.LENGTH_SHORT).show(); 
    	li=item;
    	switch (item) {
		case 0:
			SetBright(1.0F);
			break;
		case 1:
			SetBright(0.75F);
			break;
		case 2:
			SetBright(0.5F);
			break;
		case 3:
			SetBright(0.25F);
			break;
		case 4:
			SetBright(0.1F);
			break;
		default:
			SetBright(1.0F);
			break;
		}	
    	dialog.cancel(); 
    	} 
    	}).show();//显示对话框 
    }
    /**
     * 显示关于我们
     */
    public void about(){
    	new AlertDialog.Builder(ColorLightActivity.this).setTitle("关于我")
				.setMessage("欢迎您使用阿小信手电筒").setIcon(R.drawable.icon)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// finish();
					}
				}).setNegativeButton("返回",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub

							}

						}).show();
    }
    
    
    /**
     * 全屏设置
     */
    private void HideStatusBase()
	{
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		int flag=WindowManager.LayoutParams.FLAG_FULLSCREEN;
		Window myWindow=this.getWindow();
		myWindow.setFlags(flag,flag);
	}
    
    
    /**
     * 设置屏幕颜色
     * @param color_M
     */
    private void SetColor(int color_1)
    {
    	myColor = getBaseContext().getResources();
		Drawable color_M = myColor.getDrawable(color_1);
    	mylayout.setBackgroundDrawable(color_M);
        //mylayout.setBackgroundColor(Color.argb(255, 255, 255, 255));
    }
    
    /**
     * 设置屏幕亮度
     * @param light
     */
    private void SetBright(float light)
    {
    	WindowManager.LayoutParams lp=getWindow().getAttributes();
    	lp.screenBrightness=light;
    	getWindow().setAttributes(lp);
    	
    }
}