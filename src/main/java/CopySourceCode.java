import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.xdebugger.impl.ui.tree.actions.XDebuggerTreeActionBase;
import com.intellij.xdebugger.impl.ui.tree.nodes.XValueNodeImpl;
import org.jetbrains.annotations.NotNull;

public class CopySourceCode extends XDebuggerTreeActionBase {
    @Override
    protected boolean isEnabled(@NotNull XValueNodeImpl node, @NotNull AnActionEvent e) {
        return true;
    }

    @Override
    protected void perform(XValueNodeImpl node, @NotNull String nodeName, AnActionEvent e) {
        XValueNodeImpl selectedNode = XDebuggerTreeActionBase.getSelectedNode(e.getDataContext());
        System.out.println(selectedNode);
    }
}
