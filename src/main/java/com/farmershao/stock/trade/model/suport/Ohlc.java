package com.farmershao.stock.trade.model.suport;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Preconditions;

/**
 * Created by kongkp on 2017/2/7.
 */
public class Ohlc {
    private long created = System.currentTimeMillis();
    private long[] times;
    private float[] opens;
    private float[] highs;
    private float[] lows;
    private float[] closes;
    private long[] volumes;
    private float[] monies;

    private static final String DATE_FMT1 = "yyyyMMdd";//2017-02-07 11:19
    private static final String DATE_FMT2 = "yyyy-MM-dd HH:mm";//2017-02-07 11:19

    public Ohlc() {
        this(0);
    }

    public Ohlc(int size) {
        this.times = new long[size];
        this.opens = new float[size];
        this.closes = new float[size];
        this.highs = new float[size];
        this.lows = new float[size];
        this.volumes = new long[size];
        this.monies = new float[size];
    }

    public static Ohlc toOhlc(DataTable dataTable) throws ParseException {
        Preconditions.checkArgument(dataTable != null, "dataTable不能为null");

        int len = dataTable.rows();
        if (len > 0) {
            Ohlc ohlc = new Ohlc(len);
            String simpleDatetime = dataTable.getRow(0)[0];
            DateFormat df = getDateFormat(simpleDatetime);
            if (df == null) {
                throw new ParseException("无法识别日期格式:" + simpleDatetime, 0);
            }
            for (int i = 0; i < len; i++) {
                String[] row = dataTable.getRow(i);
                ohlc.times[i] = df.parse(row[0]).getTime();
                ohlc.opens[i] = Float.valueOf(StringUtils.trim(row[1]));
                ohlc.closes[i] = Float.valueOf(StringUtils.trim(row[2]));
                ohlc.highs[i] = Float.valueOf(StringUtils.trim(row[3]));
                ohlc.lows[i] = Float.valueOf(StringUtils.trim(row[4]));
                ohlc.volumes[i] = Long.valueOf(StringUtils.trim(row[5]));
                ohlc.monies[i] = Float.valueOf(StringUtils.trim(row[6]));
            }
            return ohlc;
        }
        return new Ohlc(0);
    }

    private static final DateFormat getDateFormat(String datetime) {
        if (datetime.length() == DATE_FMT1.length()) {
            return new SimpleDateFormat(DATE_FMT1);
        } else if (datetime.length() == DATE_FMT2.length()) {
            return new SimpleDateFormat(DATE_FMT2);
        } else {
            return null;
        }
    }

    public long[] getTimes() {
        return times;
    }

    public float[] getOpens() {
        return opens;
    }

    public float[] getHighs() {
        return highs;
    }

    public float[] getLows() {
        return lows;
    }

    public float[] getCloses() {
        return closes;
    }

    public long[] getVolumes() {
        return volumes;
    }

    public float[] getMonies() {
        return monies;
    }

    public long getCreated() {
        return created;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        int len = closes.length;
        sb.append("[").append("\n");
        for (int i = 0; i < len; i++) {
            sb.append("[");
            sb.append(times[i]).append(",");
            sb.append(opens[i]).append(",");
            sb.append(highs[i]).append(",");
            sb.append(lows[i]).append(",");
            sb.append(closes[i]).append(",");
            sb.append(volumes[i]).append(",");
            sb.append(monies[i]);
            sb.append("]");
            if (i < len - 1) {
                sb.append(",");
            }
            sb.append("\n");
        }
        sb.append("]");
        return sb.toString();
    }

    public static String toJsonp(Ohlc ohlc, String callback) {
        StringBuilder sb = new StringBuilder();
        sb.append(callback == null ? "callback" : callback).append("(").append("\n");
        sb.append("[").append("\n");
        if (ohlc != null) {
            for (int i = 0, len = ohlc.closes.length; i < len; i++) {
                sb.append("[");
                sb.append(ohlc.times[i]).append(",");
                sb.append(ohlc.opens[i]).append(",");
                sb.append(ohlc.highs[i]).append(",");
                sb.append(ohlc.lows[i]).append(",");
                sb.append(ohlc.closes[i]).append(",");
                sb.append(ohlc.volumes[i]).append(",");
                sb.append(ohlc.monies[i]);
                sb.append("]");
                if (i < len - 1) {
                    sb.append(",");
                }
                sb.append("\n");
            }
        }
        sb.append("]);");
        return sb.toString();
    }
}
