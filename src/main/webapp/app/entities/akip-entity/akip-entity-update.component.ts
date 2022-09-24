import { Component, Vue, Inject } from 'vue-property-decorator';

import AkipProcessService from '@/entities/akip-process/akip-process.service';
import { IAkipProcess } from '@/shared/model/akip-process.model';

import AkipApplicationService from '@/entities/akip-application/akip-application.service';
import { IAkipApplication } from '@/shared/model/akip-application.model';

import { IAkipEntity, AkipEntity } from '@/shared/model/akip-entity.model';
import AkipEntityService from './akip-entity.service';

const validations: any = {
  akipEntity: {
    name: {},
    fields: {},
    relations: {},
    type: {},
  },
};

@Component({
  validations,
})
export default class AkipEntityUpdate extends Vue {
  @Inject('akipEntityService') private akipEntityService: () => AkipEntityService;
  public akipEntity: IAkipEntity = new AkipEntity();

  @Inject('akipProcessService') private akipProcessService: () => AkipProcessService;

  public akipProcesses: IAkipProcess[] = [];

  @Inject('akipApplicationService') private akipApplicationService: () => AkipApplicationService;

  public akipApplications: IAkipApplication[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.akipEntityId) {
        vm.retrieveEntity(to.params.akipEntityId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.akipEntity.id) {
      this.akipEntityService()
        .update(this.akipEntity)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('akipGenerationWebApp.akipEntity.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.akipEntityService()
        .create(this.akipEntity)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('akipGenerationWebApp.akipEntity.created', { param: param.id });
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    }
  }

  public retrieveEntity(entityId): void {
    this.akipEntityService()
      .find(entityId)
      .then(res => {
        this.akipEntity = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.akipProcessService()
      .retrieve()
      .then(res => {
        this.akipProcesses = res.data;
      });
    this.akipApplicationService()
      .retrieve()
      .then(res => {
        this.akipApplications = res.data;
      });
  }
}
