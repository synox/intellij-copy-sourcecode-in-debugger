import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

public class Icons {
    private static Icon load(String path) {
        return IconLoader.getIcon(path, Icons.class);
    }

    public static final Icon Stream_debugger = load("/icons/stream_debugger.svg"); // 16x16
    public static final Icon Tab = load("/icons/tab.png"); // 16x16

}
