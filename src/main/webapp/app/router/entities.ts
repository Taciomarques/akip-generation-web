import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore

// prettier-ignore
const AkipApplication = () => import('@/entities/akip-application/akip-application.vue');
// prettier-ignore
const AkipApplicationUpdate = () => import('@/entities/akip-application/akip-application-update.vue');
// prettier-ignore
const AkipApplicationDetails = () => import('@/entities/akip-application/akip-application-details.vue');
// prettier-ignore
const AkipProcess = () => import('@/entities/akip-process/akip-process.vue');
// prettier-ignore
const AKipProcessDetails = () => import('@/entities/akip-process/akip-process-details.vue');
// prettier-ignore
const AkipEntity = () => import('@/entities/akip-entity/akip-entity.vue');
// prettier-ignore
const AkipEntityUpdate = () => import('@/entities/akip-entity/akip-entity-update.vue');
// prettier-ignore
const AkipEntityDetails = () => import('@/entities/akip-entity/akip-entity-details.vue');
// prettier-ignore
const GenerationProcessDetails = () => import('@/entities/generation-process/generation-process-details.vue');
// prettier-ignore
const GenerationProcessList = () => import('@/entities/generation-process/generation-process-list.vue');
// prettier-ignore
const GenerationProcessStartFormInit = () => import('@/entities/generation-process/generation-process-start-form-init.vue');
// prettier-ignore
const GenerationProcess_TaskGenerateDomainEntityDetails = () => import('@/entities/generation-process/task-generate-domain-entity/task-generate-domain-entity-details.vue');
// prettier-ignore
const GenerationProcess_TaskGenerateDomainEntityExecute = () => import('@/entities/generation-process/task-generate-domain-entity/task-generate-domain-entity-execute.vue');
// prettier-ignore
const GenerationProcess_TaskProvideProcessBpmnDetails = () => import('@/entities/generation-process/task-provide-process-bpmn/task-provide-process-bpmn-details.vue');
// prettier-ignore
const GenerationProcess_TaskProvideProcessBpmnExecute = () => import('@/entities/generation-process/task-provide-process-bpmn/task-provide-process-bpmn-execute.vue');
// prettier-ignore
const GenerationProcess_TaskGenerateProcessBindingDetails = () => import('@/entities/generation-process/task-generate-process-binding/task-generate-process-binding-details.vue');
// prettier-ignore
const GenerationProcess_TaskGenerateProcessBindingExecute = () => import('@/entities/generation-process/task-generate-process-binding/task-generate-process-binding-execute.vue');
// prettier-ignore
const GenerationProcess_TaskGenerateStartFormProcessDetails = () => import('@/entities/generation-process/task-generate-start-form-process/task-generate-start-form-process-details.vue');
// prettier-ignore
const GenerationProcess_TaskGenerateStartFormProcessExecute = () => import('@/entities/generation-process/task-generate-start-form-process/task-generate-start-form-process-execute.vue');
// prettier-ignore
const GenerationProcess_TaskGenerateUserTaskProcessDetails = () => import('@/entities/generation-process/task-generate-user-task-process/task-generate-user-task-process-details.vue');
// prettier-ignore
const GenerationProcess_TaskGenerateUserTaskProcessExecute = () => import('@/entities/generation-process/task-generate-user-task-process/task-generate-user-task-process-execute.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default [
  {
    path: '/akip-application',
    name: 'AkipApplication',
    component: AkipApplication,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/akip-application/new',
    name: 'AkipApplicationCreate',
    component: AkipApplicationUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/akip-application/:akipApplicationId/edit',
    name: 'AkipApplicationEdit',
    component: AkipApplicationUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/akip-application/:akipApplicationId/view',
    name: 'AkipApplicationView',
    component: AkipApplicationDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/akip-process',
    name: 'AkipProcess',
    component: AkipProcess,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/akip-process/:akipProcessId/view',
    name: 'AkipProcessView',
    component: AKipProcessDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/akip-entity',
    name: 'AkipEntity',
    component: AkipEntity,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/akip-entity/new',
    name: 'AkipEntityCreate',
    component: AkipEntityUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/akip-entity/:akipEntityId/edit',
    name: 'AkipEntityEdit',
    component: AkipEntityUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/akip-entity/:akipEntityId/view',
    name: 'AkipEntityView',
    component: AkipEntityDetails,
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
    path: '/process-definition/GenerationProcess/task/TaskGenerateDomainEntity/:taskInstanceId/view',
    name: 'GenerationProcess_TaskGenerateDomainEntityDetails',
    component: GenerationProcess_TaskGenerateDomainEntityDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/GenerationProcess/task/TaskGenerateDomainEntity/:taskInstanceId/execute',
    name: 'GenerationProcess_TaskGenerateDomainEntityExecute',
    component: GenerationProcess_TaskGenerateDomainEntityExecute,
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
  {
    path: '/process-definition/GenerationProcess/task/TaskGenerateProcessBinding/:taskInstanceId/view',
    name: 'GenerationProcess_TaskGenerateProcessBindingDetails',
    component: GenerationProcess_TaskGenerateProcessBindingDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/GenerationProcess/task/TaskGenerateProcessBinding/:taskInstanceId/execute',
    name: 'GenerationProcess_TaskGenerateProcessBindingExecute',
    component: GenerationProcess_TaskGenerateProcessBindingExecute,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/GenerationProcess/task/TaskGenerateStartFormProcess/:taskInstanceId/view',
    name: 'GenerationProcess_TaskGenerateStartFormProcessDetails',
    component: GenerationProcess_TaskGenerateStartFormProcessDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/GenerationProcess/task/TaskGenerateStartFormProcess/:taskInstanceId/execute',
    name: 'GenerationProcess_TaskGenerateStartFormProcessExecute',
    component: GenerationProcess_TaskGenerateStartFormProcessExecute,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/GenerationProcess/task/TaskGenerateUserTaskProcess/:taskInstanceId/view',
    name: 'GenerationProcess_TaskGenerateUserTaskProcessDetails',
    component: GenerationProcess_TaskGenerateUserTaskProcessDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/GenerationProcess/task/TaskGenerateUserTaskProcess/:taskInstanceId/execute',
    name: 'GenerationProcess_TaskGenerateUserTaskProcessExecute',
    component: GenerationProcess_TaskGenerateUserTaskProcessExecute,
    meta: { authorities: [Authority.USER] },
  },
  // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
];
