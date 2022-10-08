import { Component } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import TaskConfigureServiceTaskProcessService from './task-configure-service-task-process.service';
import { TaskConfigureServiceTaskProcessContext } from './task-configure-service-task-process.model';

@Component
export default class TaskConfigureServiceTaskProcessExecuteComponent extends mixins(JhiDataUtils) {
  private taskConfigureServiceTaskProcessService: TaskConfigureServiceTaskProcessService = new TaskConfigureServiceTaskProcessService();
  private taskContext: TaskConfigureServiceTaskProcessContext = {};
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
    this.taskConfigureServiceTaskProcessService.claim(taskInstanceId).then(res => {
      this.taskContext = res;
      this.isSaving = false;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }

  public complete() {
    this.isSaving = true;

    this.taskConfigureServiceTaskProcessService.complete(this.taskContext).then(res => {
      this.isSaving = false;
      this.$router.go(-1);
    });
  }

  public initRelationships(): void {}
}
