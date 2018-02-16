package domain;

import java.util.List;

/**
 *
 * @author Administrator
 */
@SuppressWarnings("hiding")
public class PageBean<Object>
{
    private int pc;//��ǰҳ��page code
    private int tp;//��ҳ��total pages
    private int tr;//�ܼ�¼��tatal records
    private int pr;//ÿҳ��¼��page records
    private List<Object> beanList;//��ǰҳ�ļ�¼
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPc() {
        return pc;
    }

    public void setPc(int pc) {
        this.pc = pc;
    }

    public int getTp()
    {
        int tp=tr/pr;
        return tr % pr == 0 ? tp : tp + 1 ;
    }



    public int getTr() {
        return tr;
    }

    public void setTr(int tr) {
        this.tr = tr;
    }

    public int getPr() {
        return pr;
    }

    public void setPr(int pr) {
        this.pr = pr;
    }

    public List<Object> getBeanList() {
        return beanList;
    }

    public void setBeanList(List<Object> beanList) {
        this.beanList = beanList;
    }
}

