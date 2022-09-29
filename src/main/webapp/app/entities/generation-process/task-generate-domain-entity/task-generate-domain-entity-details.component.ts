import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import TaskGenerateDomainEntityService from './task-generate-domain-entity.service';
import { TaskGenerateDomainEntityContext } from './task-generate-domain-entity.model';

@Component
export default class TaskGenerateDomainEntityDetailsComponent extends mixins(JhiDataUtils) {
  private taskGenerateDomainEntityService: TaskGenerateDomainEntityService = new TaskGenerateDomainEntityService();
  private taskContext: TaskGenerateDomainEntityContext = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskInstanceId) {
        vm.retrieveContext(to.params.taskInstanceId);
      }
    });
  }

  public retrieveContext(taskInstanceId) {
    this.taskGenerateDomainEntityService.loadContext(taskInstanceId).then(res => {
      this.taskContext = res;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
