package azunaapps.slimefrenzy;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import static android.util.FloatMath.sqrt;

/**
 * Created by Shino on 18/08/2014.
 */
public class SlimeSurfaceView extends SurfaceView
        implements SurfaceHolder.Callback {

    private int canvasWidth;
    private int canvasHeight;
    private float SPEED = 0.5f;
    private float slimeX;
    private float slimeY;
    private float headingX;
    private float headingY;


    private ArrayList<Slime> slimeTrouble = new ArrayList<Slime>();
    private ArrayList<Slime> backgroundSlime = new ArrayList<Slime>();
    private Bitmap slimeLeft = BitmapFactory.decodeResource(getResources(),R.drawable.s1);
    private Bitmap slimeRight = BitmapFactory.decodeResource(getResources(),R.drawable.s2);
    private Bitmap slimeLeft2 = BitmapFactory.decodeResource(getResources(),R.drawable.s1a);
    private Bitmap slimeRight2 = BitmapFactory.decodeResource(getResources(),R.drawable.s2a);
    private Bitmap slimeLeft3 = BitmapFactory.decodeResource(getResources(),R.drawable.s1b);
    private Bitmap slimeRight3 = BitmapFactory.decodeResource(getResources(),R.drawable.s2b);
    private Bitmap slimeA = BitmapFactory.decodeResource(getResources(),R.drawable.s4);
    private Bitmap slimeB = BitmapFactory.decodeResource(getResources(),R.drawable.s5);
    private Bitmap slimeC = BitmapFactory.decodeResource(getResources(),R.drawable.s6);
    private Bitmap slimeA2 = BitmapFactory.decodeResource(getResources(),R.drawable.s4a);
    private Bitmap slimeB2 = BitmapFactory.decodeResource(getResources(),R.drawable.s5a);
    private Bitmap slimeC2 = BitmapFactory.decodeResource(getResources(),R.drawable.s6a);
    private Bitmap slimeA3 = BitmapFactory.decodeResource(getResources(),R.drawable.s4b);
    private Bitmap slimeB3 = BitmapFactory.decodeResource(getResources(),R.drawable.s5b);
    private Bitmap slimeC3 = BitmapFactory.decodeResource(getResources(),R.drawable.s6b);
    private Bitmap backgroundBitmap;
    Random random = new Random();
    SlimeThread thread;
    Context ctx = null;
    private SurfaceHolder sh;

    public SlimeSurfaceView(Context context) {
        super(context);
        sh = getHolder();
        sh.addCallback(this);
        ctx = context;
        setFocusable(true);
    }

    public void surfaceCreated(SurfaceHolder holder) {

        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        canvasWidth = size.x;
        canvasHeight = size.y;
        backgroundBitmap = Bitmap.createBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.grass), 0, 0, canvasWidth, canvasHeight);

        Canvas canvas = sh.lockCanvas();
        canvas.drawBitmap(backgroundBitmap, 0, 0, null);
        sh.unlockCanvasAndPost(canvas);

        thread = new SlimeThread(sh, ctx, new Handler());
        thread.setRunning(true);
        thread.start();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        thread.setSurfaceSize(width, height);
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
                thread.setRunning(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            float clickX = event.getX();
            float clickY = event.getY();
            for (int i = slimeTrouble.size() - 1; i>=0; i--) {
                int s = slimeTrouble.get(i).getSlimeState();
                int height = 0;
                int width = 0;
                if (slimeTrouble.get(i).getScale() == 1) {
                    if (s > 25) {
                        height = 70;
                        width = 100;
                        if (s > 45) {
                            height = 55;
                            width = 100;
                            if (s > 65) {
                                height = 40;
                                width = 100;
                                if (s > 85) {
                                    height = 55;
                                    width = 100;
                                }
                            }
                        }
                    }
                }
                if (slimeTrouble.get(i).getScale() == 2) {
                    if (s > 25) {
                        height = 50;
                        width = 70;
                        if (s > 45) {
                            height = 40;
                            width = 70;
                            if (s > 65) {
                                height = 30;
                                width = 70;
                                if (s > 85) {
                                    height = 40;
                                    width = 70;
                                }
                            }
                        }
                    }
                }
                if (slimeTrouble.get(i).getScale() == 3) {
                    if (s > 25) {
                        height = 35;
                        width = 50;
                        if (s > 45) {
                            height = 25;
                            width = 50;
                            if (s > 65) {
                                height = 20;
                                width = 50;
                                if (s > 85) {
                                    height = 25;
                                    width = 50;
                                }
                            }
                        }
                    }
                }
                if (clickX > slimeTrouble.get(i).getX()
                        && (clickX < slimeTrouble.get(i).getX() + width)) {
                    if (clickY > slimeTrouble.get(i).getY()
                            && (clickY < slimeTrouble.get(i).getY() + height)) {
                        if ((clickX > slimeTrouble.get(i).getX() + width/4)
                                && (clickX < slimeTrouble.get(i).getX() + 3*width/4)) {
                            if ((clickY > slimeTrouble.get(i).getY() + height/4)
                                    && (clickY < slimeTrouble.get(i).getY() + 3*height/4)) {
                                if (slimeTrouble.get(i).getSlimeState() > 25) {
                                    slimeTrouble.get(i).setSlimeState(0);
                                    break;
                                }
                            }
                        }
                        if (slimeTrouble.get(i).getSlimeState() > 25) {
                            slimeTrouble.get(i).setSlimeState(1);

                            calcOppositeHeading(i, clickX, clickY, slimeTrouble.get(i).getX() + width/2, slimeTrouble.get(i).getY() + height/2);
                            break;
                        }
                    }
                }
            }
        }
        return true;
    }

    public void calcOppositeHeading(int i, float clickX, float clickY, float slimeX, float SlimeY) {
        float headingX = -(clickX - slimeX)/sqrt((clickX - slimeX)*(clickX - slimeX) + (clickY - SlimeY)*(clickY - SlimeY));
        float headingY = -(clickY - SlimeY)/sqrt((clickX - slimeX)*(clickX - slimeX) + (clickY - SlimeY)*(clickY - SlimeY));
        slimeTrouble.get(i).setHeadingX(headingX);
        slimeTrouble.get(i).setHeadingY(headingY);
    }


    public class SlimeThread extends Thread {
        private boolean run = false;

        public SlimeThread(SurfaceHolder surfaceHolder, Context context, Handler handler) {
            sh = surfaceHolder;
            ctx = context;
        }

        public void doStart() {
            Timer mTimer = new Timer();
            mTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    synchronized (sh) {
                        slimeX = random.nextInt((int)2.5*canvasWidth/4 - canvasWidth/4) + canvasWidth/4;
                        slimeY = random.nextInt((int)2.5*canvasHeight/4 - canvasHeight/4) + canvasHeight/4;
                        headingX = (float) (-1 + (Math.random() * 2));
                        headingY = (float) (-1 + (Math.random() * 2));

                        Slime Slime = new Slime(26, 1, slimeX, slimeY, headingX, headingY, SPEED);
                        slimeTrouble.add(Slime);
                    }
                }
            }, 0, 2000);
        }

        public void run() {

            while (run) {
                Canvas c = null;
                try {
                    c = sh.lockCanvas(null);
                    synchronized (sh) {
                        onDraw(c);
                    }
                } finally {
                    if (c != null) {
                        sh.unlockCanvasAndPost(c);
                    }
                }
            }
        }

        public void setRunning(boolean b) {
            run = b;
        }

        public void setSurfaceSize(int width, int height) {
            synchronized (sh) {
                canvasWidth = width;
                canvasHeight = height;
                doStart();
            }
        }

        private void onDraw(Canvas canvas) {
            canvas.drawBitmap(backgroundBitmap, 0, 0, null);

            int count = slimeTrouble.size() - 1;
            if (count >= 0) {
                for (int i = count; i >= 0; i--) {
                    int s = slimeTrouble.get(i).getSlimeState();
                    int height = 0;
                    int width = 0;
                    if (slimeTrouble.get(i).getScale() == 1) {
                        if (s > 25) {
                            height = 70;
                            width = 100;
                            if (s > 45) {
                                height = 55;
                                width = 100;
                                if (s > 65) {
                                    height = 40;
                                    width = 100;
                                    if (s > 85) {
                                        height = 55;
                                        width = 100;
                                    }
                                }
                            }
                        }
                    }
                    if (slimeTrouble.get(i).getScale() == 2) {
                        if (s > 25) {
                            height = 50;
                            width = 70;
                            if (s > 45) {
                                height = 40;
                                width = 70;
                                if (s > 65) {
                                    height = 30;
                                    width = 70;
                                    if (s > 85) {
                                        height = 40;
                                        width = 70;
                                    }
                                }
                            }
                        }
                    }
                    if (slimeTrouble.get(i).getScale() == 3) {
                        if (s > 25) {
                            height = 35;
                            width = 50;
                            if (s > 45) {
                                height = 25;
                                width = 50;
                                if (s > 65) {
                                    height = 20;
                                    width = 50;
                                    if (s > 85) {
                                        height = 25;
                                        width = 50;
                                    }
                                }
                            }
                        }
                    }
                    if (slimeTrouble.get(i).getSlimeState() == 0) {
                        slimeTrouble.remove(i);
                    } else {
                        slimeX = slimeTrouble.get(i).getX() + (slimeTrouble.get(i).getHeadingX() * slimeTrouble.get(i).getSpeed());
                        slimeY = slimeTrouble.get(i).getY() + (slimeTrouble.get(i).getHeadingY() * slimeTrouble.get(i).getSpeed());

                        if (slimeTrouble.get(i).getScale() > 3) {
                            slimeTrouble.remove(i);
                        }
                        else if (slimeX <= 0) {
                            Slime Slime1 = new Slime(26, slimeTrouble.get(i).getScale() + 1, 0, slimeY, (float) Math.random(), (float) (-1 + (Math.random() * 2)), slimeTrouble.get(i).getSpeed() + 0.8f);
                            Slime Slime2 = new Slime(26, slimeTrouble.get(i).getScale() + 1, 0, slimeY, (float) Math.random(), (float) (-1 + (Math.random() * 2)), slimeTrouble.get(i).getSpeed() + 0.8f);
                            backgroundSlime.add(Slime1);
                            backgroundSlime.add(Slime2);
                            slimeTrouble.remove(i);
                        } else if (slimeX + width >= canvasWidth) {
                            Slime Slime1 = new Slime(26, slimeTrouble.get(i).getScale() + 1, canvasWidth - width, slimeY, -(float) Math.random(), (float) (-1 + (Math.random() * 2)), slimeTrouble.get(i).getSpeed() + 0.8f);
                            Slime Slime2 = new Slime(26, slimeTrouble.get(i).getScale() + 1, canvasWidth - width, slimeY, -(float) Math.random(), (float) (-1 + (Math.random() * 2)), slimeTrouble.get(i).getSpeed() + 0.8f);
                            backgroundSlime.add(Slime1);
                            backgroundSlime.add(Slime2);
                            slimeTrouble.remove(i);
                        } else if (slimeY <= 0) {
                            Slime Slime1 = new Slime(26, slimeTrouble.get(i).getScale() + 1, slimeX, 0, (float) (-1 + (Math.random() * 2)), (float) Math.random(), slimeTrouble.get(i).getSpeed() + 0.8f);
                            Slime Slime2 = new Slime(26, slimeTrouble.get(i).getScale() + 1, slimeX, 0, (float) (-1 + (Math.random() * 2)), (float) Math.random(), slimeTrouble.get(i).getSpeed() + 0.8f);
                            backgroundSlime.add(Slime1);
                            backgroundSlime.add(Slime2);
                            slimeTrouble.remove(i);
                        } else if (slimeY + height >= canvasHeight) {
                            Slime Slime1 = new Slime(26, slimeTrouble.get(i).getScale() + 1, slimeX, canvasHeight - height, (float) (-1 + (Math.random() * 2)), -(float) Math.random(), slimeTrouble.get(i).getSpeed() + 0.8f);
                            Slime Slime2 = new Slime(26, slimeTrouble.get(i).getScale() + 1, slimeX, canvasHeight - height, (float) (-1 + (Math.random() * 2)), -(float) Math.random(), slimeTrouble.get(i).getSpeed() + 0.8f);
                            backgroundSlime.add(Slime1);
                            backgroundSlime.add(Slime2);
                            slimeTrouble.remove(i);
                        }
                        else if (slimeTrouble.get(i).getSlimeState() == 105) {
                            slimeTrouble.get(i).setSlimeState(26);
                            if (slimeTrouble.get(i).getScale() == 1) {
                                canvas.drawBitmap(slimeB, slimeX, slimeY, null);
                            }
                            else if (slimeTrouble.get(i).getScale() == 2) {
                                canvas.drawBitmap(slimeB2, slimeX, slimeY, null);
                            }
                            else {
                                canvas.drawBitmap(slimeB3, slimeX, slimeY, null);
                            }
                            slimeTrouble.get(i).setX(slimeX);
                            slimeTrouble.get(i).setY(slimeY);
                        }
                        else if (slimeTrouble.get(i).getSlimeState() > 85) {
                            slimeTrouble.get(i).setSlimeState(slimeTrouble.get(i).getSlimeState() + 1);
                            if (slimeTrouble.get(i).getScale() == 1) {
                                canvas.drawBitmap(slimeB, slimeX, slimeY, null);
                            }
                            else if (slimeTrouble.get(i).getScale() == 2) {
                                canvas.drawBitmap(slimeB2, slimeX, slimeY, null);
                            }
                            else {
                                canvas.drawBitmap(slimeB3, slimeX, slimeY, null);
                            }
                            slimeTrouble.get(i).setX(slimeX);
                            slimeTrouble.get(i).setY(slimeY);
                        }
                        else if (slimeTrouble.get(i).getSlimeState() > 65) {
                            slimeTrouble.get(i).setSlimeState(slimeTrouble.get(i).getSlimeState() + 1);
                            if (slimeTrouble.get(i).getScale() == 1) {
                                canvas.drawBitmap(slimeC, slimeX, slimeY, null);
                            }
                            else if (slimeTrouble.get(i).getScale() == 2) {
                                canvas.drawBitmap(slimeC2, slimeX, slimeY, null);
                            }
                            else {
                                canvas.drawBitmap(slimeC3, slimeX, slimeY, null);
                            }
                            slimeTrouble.get(i).setX(slimeX);
                            slimeTrouble.get(i).setY(slimeY);
                        }
                        else if (slimeTrouble.get(i).getSlimeState() > 45) {
                            slimeTrouble.get(i).setSlimeState(slimeTrouble.get(i).getSlimeState() + 1);
                            if (slimeTrouble.get(i).getScale() == 1) {
                                canvas.drawBitmap(slimeB, slimeX, slimeY, null);
                            }
                            else if (slimeTrouble.get(i).getScale() == 2) {
                                canvas.drawBitmap(slimeB2, slimeX, slimeY, null);
                            }
                            else {
                                canvas.drawBitmap(slimeB3, slimeX, slimeY, null);
                            }
                            slimeTrouble.get(i).setX(slimeX);
                            slimeTrouble.get(i).setY(slimeY);
                        }
                        else if (slimeTrouble.get(i).getSlimeState() > 25) {
                            slimeTrouble.get(i).setSlimeState(slimeTrouble.get(i).getSlimeState() + 1);
                            if (slimeTrouble.get(i).getScale() == 1) {
                                canvas.drawBitmap(slimeA, slimeX, slimeY, null);
                            }
                            else if (slimeTrouble.get(i).getScale() == 2) {
                                canvas.drawBitmap(slimeA2, slimeX, slimeY, null);
                            }
                            else {
                                canvas.drawBitmap(slimeA3, slimeX, slimeY, null);
                            }
                            slimeTrouble.get(i).setX(slimeX);
                            slimeTrouble.get(i).setY(slimeY);
                        }
                        else if (slimeTrouble.get(i).getSlimeState() > 0) {
                            slimeTrouble.get(i).setSlimeState(slimeTrouble.get(i).getSlimeState() + 1);
                            if (slimeTrouble.get(i).getScale() == 1) {
                                if (slimeTrouble.get(i).getHeadingX() > 0) {
                                    canvas.drawBitmap(slimeRight, slimeX, slimeY, null);
                                } else {
                                    canvas.drawBitmap(slimeLeft, slimeX, slimeY, null);
                                }
                            }
                            else if (slimeTrouble.get(i).getScale() == 2) {
                                if (slimeTrouble.get(i).getHeadingX() > 0) {
                                    canvas.drawBitmap(slimeRight2, slimeX, slimeY, null);
                                } else {
                                    canvas.drawBitmap(slimeLeft2, slimeX, slimeY, null);
                                }
                            }
                            else {
                                if (slimeTrouble.get(i).getHeadingX() > 0) {
                                    canvas.drawBitmap(slimeRight3, slimeX, slimeY, null);
                                } else {
                                    canvas.drawBitmap(slimeLeft3, slimeX, slimeY, null);
                                }
                            }
                            float newHeadingX = slimeX + 3*slimeTrouble.get(i).getHeadingX();
                            float newHeadingY = slimeY + 3*slimeTrouble.get(i).getHeadingY();
                            slimeTrouble.get(i).setX(newHeadingX);
                            slimeTrouble.get(i).setY(newHeadingY);
                        }
                    }
                }
                for (Slime slime : backgroundSlime) {
                    slimeTrouble.add(slime);
                }
                backgroundSlime.clear();
            }
        }
    }
}