package com.akherbouch.wsv.drawables;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import androidx.annotation.Nullable;
import com.akherbouch.wsv.utils.CommonUtil;
import com.akherbouch.wsv.utils.DrawUtil;

import static com.akherbouch.wsv.utils.CommonUtil.compare;

/**
 * Created by AYYYOUB on 28/01/2018.
 */

public class Line extends Drawable{

    private static final float pad = 0.34f;
    private int padX = 0;
    private int padY = 0;
    private int clength;
    private int color;
    private int startColumn;
    private int startRow;
    private int endColumn;
    private int endRow;
    private float length = 0;
    private int angle = -1;
    private Paint linePaintFill;
    private Paint linePaintStrock;
    private RectF rect;

    public Line(int startColumn, int startRow, int clength, int color, int padX, int padY) {
        this.clength = clength;
        this.startColumn = startColumn;
        this.startRow = startRow;
        this.endColumn = startColumn;
        this.endRow = startRow;
        this.padX = padX;
        this.padY = padY;

        setColor(color);
    }

    public Line(String lineStr, int clength, int padX, int padY) {
        this.startColumn = Integer.parseInt(lineStr.split(":")[0].split(",")[0]);
        this.startRow = Integer.parseInt(lineStr.split(":")[0].split(",")[1]);
        int c = Integer.parseInt(lineStr.split(":")[1].split(",")[0]);
        int r = Integer.parseInt(lineStr.split(":")[1].split(",")[1]);
        this.clength = clength;
        this.padX = padX;
        this.padY = padY;

        setColor(0x00);
        setEndPos(c,r);
    }

    public void setColor(int color) {
        this.color = color;
        this.linePaintStrock = DrawUtil.INSTANCE.createStrokePaint(0xff000000+color, 1f);
        this.linePaintFill = DrawUtil.INSTANCE.createFillPaint(0x77000000+this.color);
    }

    public void setEndPos(int column, int row){
        this.endColumn = column;
        this.endRow = row;
        length = (float) Math.sqrt(Math.pow(startColumn-endColumn,2) + Math.pow(startRow-endRow,2));
        angle = getAngle();
        int x1 = (int) (-pad*clength);
        int y1 = (int) (-pad*clength);
        int x2 = (int) (clength*length + pad*clength);
        int y2 = (int) (pad*clength);
        rect = new RectF(x1, y1, x2, y2);
    }

    @Override
    public void draw(Canvas canvas) {
        if(angle == -1){
            canvas.drawCircle((startColumn+0.5f)*clength+ padX,(startRow+0.5f)*clength+ padY,
                    clength*pad,linePaintFill);
            canvas.drawCircle((startColumn+0.5f)*clength+ padX,(startRow+0.5f)*clength+ padY,
                    clength*pad,linePaintStrock);
        }
        else {
            canvas.save();
            canvas.translate((startColumn+0.5f)*clength+ padX,(startRow+0.5f)*clength+ padY);
            canvas.rotate(angle);
            canvas.drawRoundRect(rect,clength,clength,linePaintFill);
            canvas.drawRoundRect(rect,clength,clength,linePaintStrock);
            canvas.restore();
        }
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Line)) {
            return false;
        }
        Line line = (Line) o;
        return line.startColumn == startColumn &&
                line.startRow == startRow &&
                line.endColumn == endColumn &&
                line.endRow == endRow;
    }

    @Override
    public int hashCode() {
        return startColumn+startRow+endColumn+endRow;
    }

    private int getAngle(){
        if(startRow == endRow){
            if(startColumn == endColumn) return -1;
            if(startColumn<endColumn) return 0;
            else return -180;
        }
        else if(startColumn == endColumn){
            if(startRow<endRow) return 90;
            else return -90;
        }
        else if(startColumn-endColumn == startRow-endRow){
            if(startColumn<endColumn) return 45;
            else return -135;
        }
        else if(startColumn<endColumn) return -45;
        else return 135;
    }

    public String getWord(Object[][] M){
        int l = Math.max(Math.abs(endColumn-startColumn), Math.abs(endRow-startRow))+1;
        int c = startColumn;
        int r = startRow;
        int cc = compare(endColumn,startColumn);
        int rr = compare(endRow,startRow);
        String wr = "";
        for(int i = 0;i<l;i++){
            wr += M[c][r].toString();
            c+=cc;
            r+=rr;
        }
        return wr;
    }

    public int[] getPos(int k){
        int l = Math.max(Math.abs(endColumn-startColumn), Math.abs(endRow-startRow));
        int c = startColumn;
        int r = startRow;
        int cc = compare(endColumn,startColumn);
        int rr = compare(endRow,startRow);
        for(int i = 0;i<k;i++){
            if(i == l) return null;
            c+=cc;
            r+=rr;
        }
        return new int[]{c,r};
    }
}


