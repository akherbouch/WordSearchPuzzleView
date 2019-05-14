package com.akherbouch.wsv;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import com.akherbouch.wsv.drawables.Drawable;
import com.akherbouch.wsv.drawables.Grid;
import com.akherbouch.wsv.drawables.Letter;
import com.akherbouch.wsv.drawables.Line;
import com.akherbouch.wsv.utils.Calculator;
import com.akherbouch.wsv.utils.CommonUtil;
import com.akherbouch.wsv.utils.DrawUtil;
import com.akherbouch.wsv.utils.ExceptionUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import static com.akherbouch.wsv.utils.CommonUtil.isDiag;
import static com.akherbouch.wsv.utils.CommonUtil.sleep;
import static java.lang.Math.max;

/**
 * Created by AYYYOUB on 28/01/2018.
 */

public class WordSearchView extends SurfaceView implements Runnable, LifecycleObserver {

    private TextPaint textPaint;
    private ArrayList<Integer> colors;
    private Drawable.Builder drawableBuilder;
    private Stack<Drawable> drawableStack;
    private Calculator calculator;
    private Thread gameThread = null;
    private SurfaceHolder mHolder;
    volatile boolean playing;

    private WordSearchPuzzle puzzle;

    private Letter[][] letters;
    private List<Line> answers;
    private String state;

    private CallBacks callBacks;

    public WordSearchView(Context context) {
        super(context);
        init();
    }

    public WordSearchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WordSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mHolder = getHolder();
        mHolder.setFormat(PixelFormat.TRANSLUCENT);
        setZOrderOnTop(true);
        playing = true;
        defaultSetup();
        //((LifecycleOwner) getContext()).getLifecycle().addObserver(this);
    }

    public void registerLifecycle(Lifecycle lifecycle) {
        lifecycle.addObserver(this);
    }

    //for preview
    private void defaultSetup(){
        setup( new WordSearchPuzzle() );
    }

    public void setup(WordSearchPuzzle puzzle) {
        this.puzzle = puzzle;
        this.state = puzzle.getState();
        this.colors = new ArrayList<>();
        int[] resColors = getContext().getResources().getIntArray(R.array.colors);
        int[] indexes = CommonUtil.INSTANCE.randomIndexesWithKey(resColors.length, puzzle.getAnswers().get(0));// make list unique for each level
        for(int i : indexes)  this.colors.add(resColors[i]);
        int textSize = 100/puzzle.getRows() + puzzle.getRows()/3;
        this.textPaint = DrawUtil.INSTANCE.createTextPaintFromResources(getContext(), R.font.aab, textSize, 0xffdddddd);
    }

    private boolean isLoaded = false;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(widthMeasureSpec == 0 && heightMeasureSpec == 0) return;
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (!isLoaded) {
            initDrawables(width, height);
            loadStatus();
            isLoaded = true;
        }
    }


    private void initDrawables(int width, int height){
        int col =  puzzle.getColumns();
        int row = puzzle.getRows();
        calculator = new Calculator(width, height, col, row);
        int length = calculator.getLength();
        int padX = calculator.getPadX();
        int padY = calculator.getPadY();

        drawableBuilder = new Drawable.Builder(getContext(), length, padX, padY);
        drawableStack = new Stack<>();

        int bgWidth = width - 2* padX;
        int bgHeight = height - 2* padY;
        Drawable background = drawableBuilder.buildBackground(bgWidth,bgHeight);
        drawableStack.push(background);

        Grid grid = drawableBuilder.buildGrid(col, row);
        drawableStack.push(grid);

        letters = new Letter[col][row];
        for (int c = 0; c < col; c++) {
            for (int r = 0; r < row ; r++) {
                int ii = c + r*col;
                if(ii >= puzzle.getLetters().size())
                    throw ExceptionUtil.INSTANCE.createLetterLessThanCasesException();
                String ch = puzzle.getLetters().get(ii);
                Letter letter = drawableBuilder.buildLetter(c,r,ch,textPaint);
                drawableStack.push(letter);
                letters[c][r] = letter;
            }
        }

        answers = new LinkedList<>();
        for(String ans : puzzle.getAnswers()) {
            answers.add(drawableBuilder.buildLine(ans));
        }
    }

    private void loadStatus(){
        if(TextUtils.isEmpty(state)) return;
        String[] st = state.split(",");
        state = "";
        for(String s : st){
            if (s.equals("h")) hint();
            else solve(Integer.parseInt(s));
        }
    }


    @Override
    public void run() {
        long startFrameTime, timeThisFrame, waitTime;
        Canvas canvas;
        while (playing) {
            startFrameTime = System.currentTimeMillis();
            if(mHolder.getSurface().isValid()) {
                canvas = mHolder.lockCanvas();
                synchronized (canvas) {
                    canvas.drawColor(0, PorterDuff.Mode.CLEAR);
                    synchronized (drawableStack)  {
                        for(Drawable d : drawableStack) d.draw(canvas);
                    }
                }
                mHolder.unlockCanvasAndPost(canvas);
            }
            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            //fps = timeThisFrame > 0 ? 1000 / timeThisFrame : 1000;
            waitTime = max(24 - timeThisFrame,0);
            sleep(waitTime);
        }
    }

    private Line curLine;
    private String curWord;
    private int downCol, downRow;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN :
                downCol = calculator.getColFromX(event.getX());
                downRow = calculator.getRowFromY(event.getY());
                if(calculator.isOutOfBounds(downCol, downRow)) return true;
                curLine = drawableBuilder.buildLine(downCol, downRow, getColor());
                drawableStack.push(curLine);
                curWord = letters[downCol][downRow].toString();
                break;

            case MotionEvent.ACTION_MOVE :
                if(calculator.isOutOfBounds(downCol, downRow) ) return true;
                int moveCol = calculator.getColFromX(event.getX());
                int moveRow = calculator.getRowFromY(event.getY());
                if(calculator.isOutOfBounds(moveCol, moveRow)) return true;
                if(!isDiag(downCol, downRow,moveCol,moveRow)) return true;
                curLine.setEndPos(moveCol,moveRow);
                curWord = curLine.getWord(letters);
                break;

            case MotionEvent.ACTION_UP :
                if(calculator.isOutOfBounds(downCol, downRow)) return true;
                int res = check(curLine);
                if(res != -1) solve(res);
                drawableStack.pop();
                curLine = null;
                curWord = null;
                break;
        }

        if(callBacks != null) callBacks.onSelect(this, curWord);
        return true;
    }

    public int getColor() {
        return this.colors.get(answers.size()%colors.size());
    }

    public int check(Line line){
        for(int i = 0; i< answers.size(); i++){
            if(line.equals(answers.get(i)))
                return i;
        }
        return -1;
    }

    public void hint() {
        if(answers.size() == 0) return;
        Line ans = answers.get(0);
        int i = 0;
        int[] pos;
        while ((pos = ans.getPos(i)) != null && letters[pos[0]][pos[1]].isDiscovered()) i++;
        if(pos == null) solve(0);
        else letters[pos[0]][pos[1]].setDiscovered(true);
        state += "h,";
        if(callBacks != null) callBacks.onHint(this);
    }

    public void solve(int res){
        if(answers.size() == 0) return;
        int color = getColor();
        Line line = answers.remove(res);
        line.setColor(color);
        drawableStack.push(line);
        state += res+",";
        if(callBacks != null) {
            if(answers.size() == 0)
                callBacks.onWin(this);
            else callBacks.onSolve(this);
        }
    }

    public void setCallBacks(CallBacks callBacks) {
        this.callBacks = callBacks;
    }

    public interface CallBacks {
        void onHint(@NonNull WordSearchView view);
        void onWin(@NonNull WordSearchView view);
        void onSolve(@NonNull WordSearchView view);
        void onSelect(@NonNull WordSearchView view, String word);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void pause() {
        playing = false;
        try { gameThread.join();
        } catch (InterruptedException e) {
            Log.e("Error:", "joining thread");
        }
    }


}
