package com.akipgenerationweb.domain.enumeration;

/**
 * The StatusProcess enumeration.
 */
public enum StatusProcess {
    WAITING_PROVIDE_DOMAIN_ENTITIES,
    WAITING_PROVIDE_PROCESS_BINDING,
    WAITING_PROVIDE_PROCESS_BPMN,
    WAITING_PROVIDE_START_FORM_AND_PROCESS_TASKS,
    FINISHED,
}
