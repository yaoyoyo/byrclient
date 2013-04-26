package haitong.yao.byrclient.cache;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mr.Yao 把已读的帖子、信件放在内存中，方便判断
 */
public final class ReadItems {

    private static List<Integer> sReadArticles = new ArrayList<Integer>();

    public static void saveReadAriticle(int id) {
        if (!sReadArticles.contains(id)) {
            sReadArticles.add(id);
        }
    }

    public static void saveReadAriticles(List<Integer> articles) {
        for (int i = 0; i < articles.size(); i++) {
            if (!sReadArticles.contains(articles.get(i))) {
                sReadArticles.add(articles.get(i));
            }
        }
    }

    public static boolean isArticleRead(int id) {
        return sReadArticles.contains(id);
    }

}
