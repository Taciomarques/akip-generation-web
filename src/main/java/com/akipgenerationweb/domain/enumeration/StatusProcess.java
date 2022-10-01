package com.akipgenerationweb.domain.enumeration;

/**
 * The StatusProcess enumeration.
 */
public enum StatusProcess {
    WAITING_GENERATE_DOMAIN_ENTITY,
    WAITING_GENERATE_PROCESS_BINDING,
    WAITING_PROVIDE_PROCESS_BPMN,
    WAITING_GENERATE_START_FORM,
    WAITING_GENERATE_USERS_TASK,
    WAITING_GENERATE_SERVICES_TASK,
    FINISHED,
}
