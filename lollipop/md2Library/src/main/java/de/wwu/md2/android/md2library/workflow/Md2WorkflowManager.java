package de.wwu.md2.android.md2library.workflow;

import de.wwu.md2.android.md2library.view.management.implementation.Md2ViewManager;

/**
 * Created by c_rieg01 on 23.08.2016.
 */
public class Md2WorkflowManager {

    protected static Md2WorkflowManager instance;

    protected Md2WorkflowElement currentWorkflowElement;

    private Md2WorkflowManager() {
        // Singleton constructor
    }

    public static Md2WorkflowManager getInstance(){
        if(instance == null){
            instance = new Md2WorkflowManager();
        }
        return instance;
    }

    public void goToWorkflow(Md2WorkflowElement workflowElement){
        if(workflowElement == null) return;

        if (currentWorkflowElement != null) {
            currentWorkflowElement.end();
        }
        currentWorkflowElement = workflowElement;
        currentWorkflowElement.start();
    }

    public void endWorkflow(Md2WorkflowElement workflowElement){
        if(workflowElement != null) {
            workflowElement.end();
            currentWorkflowElement = null;
        }

        Md2ViewManager.getInstance().goToStartActivity();
    }
}
