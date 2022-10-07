import { Component, Watch } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import TaskGenerateUserTaskProcessService from './task-generate-user-task-process.service';
import { TaskGenerateUserTaskProcessContext } from './task-generate-user-task-process.model';

@Component
export default class TaskGenerateUserTaskProcessExecuteComponent extends mixins(JhiDataUtils) {
  private taskGenerateUserTaskProcessService: TaskGenerateUserTaskProcessService = new TaskGenerateUserTaskProcessService();
  private taskContext: TaskGenerateUserTaskProcessContext = {};
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
    this.taskGenerateUserTaskProcessService.claim(taskInstanceId).then(res => {
      this.taskContext = res;
      this.isSaving = false;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }

  public complete() {
    this.isSaving = true;

    this.taskGenerateUserTaskProcessService.complete(this.taskContext).then(res => {
      this.isSaving = false;
      this.$router.go(-1);
    });
  }

  public initRelationships(): void {}
}
