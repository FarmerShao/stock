package com.farmershao.stock.trade.dto;

/**
 * F10 类别 文件 开始 长度
 * @Author chenjz
 */
public class F10FileModel {

    /**
     * 类别
     */
    private String classic;

    /**
     * 文件
     */
    private String file;

    /**
     * 开始
     */
    private int start;

    /**
     * 长度
     */
    private int length;

    public void setClassic(String classic) {
        this.classic = classic;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getClassic() {
        return classic;
    }

    public String getFile() {
        return file;
    }

    public int getStart() {
        return start;
    }

    public int getLength() {
        return length;
    }


    public void read(String[] quoteDataRow)  {
        if (quoteDataRow != null && quoteDataRow.length == 4) {
         this.classic=quoteDataRow[0];
         this.file=quoteDataRow[1];
         this.start=Integer.valueOf(quoteDataRow[2]);
         this.length=Integer.valueOf(quoteDataRow[3]);
        }
    }
}
