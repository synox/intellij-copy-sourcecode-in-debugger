import com.intellij.debugger.actions.ViewAsGroup;
import com.intellij.debugger.engine.JavaValue;
import com.intellij.debugger.engine.evaluation.EvaluationContext;
import com.intellij.debugger.impl.descriptors.data.DescriptorData;
import com.intellij.debugger.impl.descriptors.data.FieldData;
import com.intellij.debugger.impl.descriptors.data.StaticFieldData;
import com.intellij.debugger.ui.impl.watch.FieldDescriptorImpl;
import com.intellij.debugger.ui.impl.watch.ValueDescriptorImpl;
import com.intellij.debugger.ui.tree.FieldDescriptor;
import com.intellij.debugger.ui.tree.NodeDescriptor;
import com.intellij.debugger.ui.tree.render.ClassRenderer;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.xdebugger.frame.XValue;
import com.intellij.xdebugger.impl.ui.tree.actions.XDebuggerTreeActionBase;
import com.intellij.xdebugger.impl.ui.tree.nodes.XValueNodeImpl;
import com.sun.jdi.*;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import com.sun.jdi.Value;

public class CopySourceCode extends XDebuggerTreeActionBase {
    @Override
    protected boolean isEnabled(@NotNull XValueNodeImpl node, @NotNull AnActionEvent e) {
        return true;
    }

    @Override
    protected void perform(XValueNodeImpl node, @NotNull String nodeName, AnActionEvent e) {
        final List<JavaValue> values = ViewAsGroup.getSelectedValues(e);
        System.out.println(values);
        for (JavaValue value : values) {
            System.out.println(value);
            Type type = value.getDescriptor().getType();
            System.out.println(type);
            ValueDescriptorImpl descriptor = value.getDescriptor();
            ValueDescriptorImpl fullValueDescriptor = descriptor.getFullValueDescriptor();
            Value value1 = fullValueDescriptor.getValue();
            Map<FieldDescriptor, Object> fieldDescriptors = buildChildren(value1, value.getEvaluationContext(), value.getDescriptor());
            System.out.println(fieldDescriptors);
        }

//        Notification.fire(new Notification("synox.test", "Copy Code", "done", NotificationType.INFORMATION), null);
        XValue valueContainer = node.getValueContainer();


        for (JavaValue javaValue : values) {
            Value innerValue = javaValue.getDescriptor().getValue();
            System.out.println(innerValue);
            if (innerValue instanceof ObjectReference) {
                ObjectReference ref = (ObjectReference) innerValue;
                String type = innerValue.type().name();
                System.out.println("type: " + type);
                ReferenceType referenceType = ref.referenceType();
                List<Field> fields = referenceType.allFields();
                System.out.println("fields: " + fields);

                Map<Field, Value> myValues = ref.getValues(fields);
                System.out.println("values: " + myValues);
            }
        }
        System.out.println("done");
    }

    /**
     * @see ClassRenderer#buildChildren(com.sun.jdi.Value, com.intellij.debugger.ui.tree.render.ChildrenBuilder, com.intellij.debugger.engine.evaluation.EvaluationContext)
     */
    static public Map<FieldDescriptor, Object> buildChildren(final Value value, final EvaluationContext evaluationContext, ValueDescriptorImpl parentDescriptor) {
        Map<FieldDescriptor, Object> children = new HashMap<>();
        if (value instanceof ObjectReference) {
            Project myProject = parentDescriptor.getProject();
            final ObjectReference objRef = (ObjectReference) value;
            final ReferenceType refType = objRef.referenceType();
            // default ObjectReference processing
            List<Field> fields = refType.allFields();
            for (Field field : fields) {
                FieldDescriptor fieldDescriptor = getFieldDescriptor(parentDescriptor, objRef, field, myProject);
                String name = fieldDescriptor.getName();
                Value theChildValue = ((ObjectReference) value).getValue(field);
                System.out.println("value.. " + theChildValue);
                children.put(fieldDescriptor, theChildValue);
            }

        }
        return children;
    }

    @NotNull
    public static FieldDescriptorImpl getFieldDescriptor(NodeDescriptor parent, ObjectReference objRef, Field field, Project myProject) {
        final DescriptorData<FieldDescriptorImpl> descriptorData;
        if (objRef == null) {
            if (!field.isStatic()) {
//                LOG.error("Object reference is null for non-static field: " + field);
            }
            descriptorData = new StaticFieldData(field);
        } else {
            descriptorData = new FieldData(objRef, field);
        }
        return getDescriptor(parent, descriptorData, myProject);
    }

    @NotNull
    public static <T extends NodeDescriptor> T getDescriptor(NodeDescriptor parent, DescriptorData<T> key, Project myProject) {
        final T descriptor = key.createDescriptor(myProject);
        return descriptor;
    }


}
