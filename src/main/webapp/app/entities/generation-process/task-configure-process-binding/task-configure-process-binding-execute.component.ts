import { Component, Watch } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import TaskConfigureProcessBindingService from './task-configure-process-binding.service';
import { TaskConfigureProcessBindingContext } from './task-configure-process-binding.model';
import { AkipEntity } from '@/shared/model/akip-entity.model';
import { required } from 'vuelidate/lib/validators';

const validations = {
  akipEntityDomain: {
    required,
  },
};

@Component({ validations })
export default class TaskConfigureProcessBindingExecuteComponent extends mixins(JhiDataUtils) {
  private taskConfigureProcessBindingService: TaskConfigureProcessBindingService = new TaskConfigureProcessBindingService();
  private taskContext: TaskConfigureProcessBindingContext = {};
  public isSaving = false;
  public isAkipEntityInvalid = true;
  public akipEntityDomain: AkipEntity = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskInstanceId) {
        vm.claimTaskInstance(to.params.taskInstanceId);
      }
    });
  }

  public claimTaskInstance(taskInstanceId) {
    this.isSaving = true;
    this.taskConfigureProcessBindingService.claim(taskInstanceId).then(res => {
      this.taskContext = res;
      this.isSaving = false;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }

  public complete() {
    this.isSaving = true;
    this.taskContext.generationProcess.akipProcess.entities.push(this.akipEntityDomain);

    this.taskConfigureProcessBindingService.complete(this.taskContext).then(res => {
      this.isSaving = false;
      this.$router.go(-1);
    });
  }

  public initRelationships(): void {}

  public setProcessBindingFieldsAndRelationshipsOfAkipEntityDomain() {
    this.taskContext.akipEntityProcessBinding.fields = this.akipEntityDomain.fields;
    this.taskContext.akipEntityProcessBinding.relationships = this.akipEntityDomain.relationships;
  }
}
