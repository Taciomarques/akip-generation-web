import { Component } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import TaskConfigureUserTaskProcessService from './task-configure-user-task-process.service';
import { TaskConfigureUserTaskProcessContext } from './task-configure-user-task-process.model';

@Component
export default class TaskConfigureUserTaskProcessExecuteComponent extends mixins(JhiDataUtils) {
  private taskConfigureUserTaskProcessService: TaskConfigureUserTaskProcessService = new TaskConfigureUserTaskProcessService();
  private taskContext: TaskConfigureUserTaskProcessContext = {};
  public isSaving = false;
  public isAkipEntityInvalid = true;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskInstanceId) {
        vm.claimTaskInstance(to.params.taskInstanceId);
      }
    });
  }

  public claimTaskInstance(taskInstanceId) {
    this.isSaving = true;
    this.taskConfigureUserTaskProcessService.claim(taskInstanceId).then(res => {
      this.taskContext = res;
      this.isSaving = false;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }

  public complete() {
    this.isSaving = true;

    this.taskConfigureUserTaskProcessService.complete(this.taskContext).then(res => {
      this.isSaving = false;
      this.$router.go(-1);
    });
  }

  public initRelationships(): void {}

  get akipEntityDomain() {
    return this.taskContext.generationProcess.akipProcess.entities.find(entity => entity.type == 'DOMAIN');
  }
}
