package de.uni_muenster.wi.md2library.workflow;

/**
 * Created by c_rieg01 on 23.08.2016.
 */
public enum Md2WorkflowEvent {
    DoneEvent("DoneEvent"),
    CancelWorkflowEvent("CancelWorkflowEvent"),
    SubmitEvent("SubmitEvent"),
    CancelComplaintWorkflowEvent("CancelComplaintWorkflowEvent");

    protected String description = "NotFound";

    Md2WorkflowEvent(String description){
        this.description = description;
    }

    // Explicit attribute to get the event name
    public String getDescription() {
        return this.description;
    }
}
