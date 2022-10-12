import { Component } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import TaskGenerateEntitiesService from './task-generate-entities.service';
import { TaskGenerateEntitiesContext } from './task-generate-entities.model';

@Component
export default class TaskGenerateEntitiesExecuteComponent extends mixins(JhiDataUtils) {
  private taskGenerateEntitiesService: TaskGenerateEntitiesService = new TaskGenerateEntitiesService();
  private taskContext: TaskGenerateEntitiesContext = {};
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskInstanceId) {
        vm.claimTaskInstance(to.params.taskInstanceId);
      }
    });
  }

  public claimTaskInstance(taskInstanceId) {
    this.taskGenerateEntitiesService.claim(taskInstanceId).then(res => {
      this.taskContext = res;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }

  public complete() {
    this.taskGenerateEntitiesService.complete(this.taskContext).then(res => {
      this.$router.go(-1);
    });
  }

  public initRelationships(): void {}
}
