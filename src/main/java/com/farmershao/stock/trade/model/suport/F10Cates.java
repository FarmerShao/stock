package com.farmershao.stock.trade.model.suport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * F10信息分类索引
 * Created by kongkp on 2017/1/10.
 */
public class F10Cates implements Serializable {
    private static final long serialVersionUID = 1L;

    private String stockCode;
    Map<String, Index> detail = new TreeMap<>();

    public F10Cates(String stockCode, String detailContent) {
        this.stockCode = stockCode;
        DataTable dt = new DataTable(detailContent);
        dt.browse(row -> {
            String cate = row[0];
            String file = row[1];
            int start = Integer.valueOf(row[2]);
            int length = Integer.valueOf(row[3]);
            detail.put(cate, new Index(file, start, length));
            return true;
        });
    }

    public Index getIndex(String cate) {
        return detail.get(cate);
    }

    public List<String> cates() {
        return new ArrayList<>(detail.keySet());
    }

    public static class Index {
        private String file;
        private int start;
        private int length;

        Index(String file, int start, int length) {
            this.file = file;
            this.start = start;
            this.length = length;
        }

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("类别\t文件\t开始\t长度").append("\n");
        Set<Map.Entry<String, Index>> entries = detail.entrySet();
        for (Map.Entry<String, Index> entry : entries) {
            Index val = entry.getValue();
            sb.append(entry.getKey()).append("\t");
            sb.append(val.getFile()).append("\t");
            sb.append(val.getStart()).append("\t");
            sb.append(val.getLength()).append("\n");
        }
        return sb.toString();
    }
}
