import { Component } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import TaskGenerateEntitiesService from './task-generate-entities.service';
import { TaskGenerateEntitiesContext } from './task-generate-entities.model';

@Component
export default class TaskGenerateEntitiesDetailsComponent extends mixins(JhiDataUtils) {
  private taskGenerateEntitiesService: TaskGenerateEntitiesService = new TaskGenerateEntitiesService();
  private taskContext: TaskGenerateEntitiesContext = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskInstanceId) {
        vm.retrieveContext(to.params.taskInstanceId);
      }
    });
  }

  public retrieveContext(taskInstanceId) {
    this.taskGenerateEntitiesService.loadContext(taskInstanceId).then(res => {
      this.taskContext = res;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
