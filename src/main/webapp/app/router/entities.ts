import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore

// prettier-ignore
const Application = () => import('@/entities/application/application.vue');
// prettier-ignore
const ApplicationUpdate = () => import('@/entities/application/application-update.vue');
// prettier-ignore
const ApplicationDetails = () => import('@/entities/application/application-details.vue');
// prettier-ignore
const Process = () => import('@/entities/process/process.vue');
// prettier-ignore
const ProcessUpdate = () => import('@/entities/process/process-update.vue');
// prettier-ignore
const ProcessDetails = () => import('@/entities/process/process-details.vue');
// prettier-ignore
const Entidade = () => import('@/entities/entidade/entidade.vue');
// prettier-ignore
const EntidadeUpdate = () => import('@/entities/entidade/entidade-update.vue');
// prettier-ignore
const EntidadeDetails = () => import('@/entities/entidade/entidade-details.vue');
// prettier-ignore
const GenerationProcessDetails = () => import('@/entities/generation-process/generation-process-details.vue');
// prettier-ignore
const GenerationProcessList = () => import('@/entities/generation-process/generation-process-list.vue');
// prettier-ignore
const GenerationProcessStartFormInit = () => import('@/entities/generation-process/generation-process-start-form-init.vue');
// prettier-ignore
const GenerationProcess_TaskProvideProcessBpmnDetails = () => import('@/entities/generation-process/task-provide-process-bpmn/task-provide-process-bpmn-details.vue');
// prettier-ignore
const GenerationProcess_TaskProvideProcessBpmnExecute = () => import('@/entities/generation-process/task-provide-process-bpmn/task-provide-process-bpmn-execute.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default [
  {
    path: '/application',
    name: 'Application',
    component: Application,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/application/new',
    name: 'ApplicationCreate',
    component: ApplicationUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/application/:applicationId/edit',
    name: 'ApplicationEdit',
    component: ApplicationUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/application/:applicationId/view',
    name: 'ApplicationView',
    component: ApplicationDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process',
    name: 'Process',
    component: Process,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process/new',
    name: 'ProcessCreate',
    component: ProcessUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process/:processId/edit',
    name: 'ProcessEdit',
    component: ProcessUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process/:processId/view',
    name: 'ProcessView',
    component: ProcessDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/entidade',
    name: 'Entidade',
    component: Entidade,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/entidade/new',
    name: 'EntidadeCreate',
    component: EntidadeUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/entidade/:entidadeId/edit',
    name: 'EntidadeEdit',
    component: EntidadeUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/entidade/:entidadeId/view',
    name: 'EntidadeView',
    component: EntidadeDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/GenerationProcess/instance/:processInstanceId/view',
    name: 'GenerationProcessView',
    component: GenerationProcessDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/GenerationProcess/instances',
    name: 'GenerationProcessList',
    component: GenerationProcessList,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/GenerationProcess/init',
    name: 'GenerationProcessStartFormInit',
    component: GenerationProcessStartFormInit,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/GenerationProcess/task/TaskProvideProcessBpmn/:taskInstanceId/view',
    name: 'GenerationProcess_TaskProvideProcessBpmnDetails',
    component: GenerationProcess_TaskProvideProcessBpmnDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/GenerationProcess/task/TaskProvideProcessBpmn/:taskInstanceId/execute',
    name: 'GenerationProcess_TaskProvideProcessBpmnExecute',
    component: GenerationProcess_TaskProvideProcessBpmnExecute,
    meta: { authorities: [Authority.USER] },
  },
  // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
];
