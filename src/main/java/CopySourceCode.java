import com.intellij.debugger.actions.ViewAsGroup;
import com.intellij.debugger.engine.JavaValue;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.xdebugger.impl.ui.tree.actions.XDebuggerTreeActionBase;
import com.intellij.xdebugger.impl.ui.tree.nodes.XValueNodeImpl;
import com.sun.jdi.Field;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.Type;
import com.sun.jdi.Value;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class CopySourceCode extends XDebuggerTreeActionBase {
    @Override
    protected boolean isEnabled(@NotNull XValueNodeImpl node, @NotNull AnActionEvent e) {
        return true;
    }

    // @see ClassRenderer#buildChildren(com.sun.jdi.Value, com.intellij.debugger.ui.tree.render.ChildrenBuilder, com.intellij.debugger.engine.evaluation.EvaluationContext)
    @Override
    protected void perform(XValueNodeImpl node, @NotNull String nodeName, AnActionEvent e) {
        for (JavaValue javaValue : ViewAsGroup.getSelectedValues(e)) {
            Value innerValue = javaValue.getDescriptor().getValue();
            if (innerValue instanceof ObjectReference) {
                ObjectReference reference = (ObjectReference) innerValue;
                List<Field> fields = reference.referenceType().allFields();
                Map<Field, Value> myValues = reference.getValues(fields);

                System.out.println(innerValue.type() + ": " + myValues);
                String code = generateCode(innerValue.type(), myValues);
                System.out.println("code: " + code);
            }
        }
    }

    private String generateCode(Type type, Map<Field, Value> myValues) {
        return "TODO";
    }


}
