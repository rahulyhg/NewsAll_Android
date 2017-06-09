package sourabh.newsall.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Downloader on 6/3/2017.
 */

public class SourcesData implements Serializable{

    List<SourceData> sources;

    public List<SourceData> getSources() {
        return sources;
    }

    public void setSources(List<SourceData> sources) {
        this.sources = sources;
    }
}
