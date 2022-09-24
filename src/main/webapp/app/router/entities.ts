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
const AkipProcessUpdate = () => import('@/entities/akip-process/akip-process-update.vue');
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
const GenerationProcess_TaskProvideProcessBpmnDetails = () => import('@/entities/generation-process/task-provide-process-bpmn/task-provide-process-bpmn-details.vue');
// prettier-ignore
const GenerationProcess_TaskProvideProcessBpmnExecute = () => import('@/entities/generation-process/task-provide-process-bpmn/task-provide-process-bpmn-execute.vue');
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
    path: '/akip-process/new',
    name: 'AkipProcessCreate',
    component: AkipProcessUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/akip-process/:akipProcessId/edit',
    name: 'AkipProcessEdit',
    component: AkipProcessUpdate,
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