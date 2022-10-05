import { Component, Watch } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import TaskGenerateStartFormProcessService from './task-generate-start-form-process.service';
import { TaskGenerateStartFormProcessContext } from './task-generate-start-form-process.model';

@Component
export default class TaskGenerateStartFormProcessExecuteComponent extends mixins(JhiDataUtils) {
  private taskGenerateStartFormProcessService: TaskGenerateStartFormProcessService = new TaskGenerateStartFormProcessService();
  private taskContext: TaskGenerateStartFormProcessContext = {};
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
    this.taskGenerateStartFormProcessService.claim(taskInstanceId).then(res => {
      this.taskContext = res;
      this.isSaving = false;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }

  public complete() {
    this.isSaving = true;

    this.taskGenerateStartFormProcessService.complete(this.taskContext).then(res => {
      this.isSaving = false;
      this.$router.go(-1);
    });
  }

  public initRelationships(): void {}
}
