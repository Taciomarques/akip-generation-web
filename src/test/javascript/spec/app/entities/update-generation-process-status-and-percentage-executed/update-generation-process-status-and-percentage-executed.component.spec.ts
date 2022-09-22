/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import UpdateGenerationProcessStatusAndPercentageExecutedComponent from '@/entities/update-generation-process-status-and-percentage-executed/update-generation-process-status-and-percentage-executed.vue';
import UpdateGenerationProcessStatusAndPercentageExecutedClass from '@/entities/update-generation-process-status-and-percentage-executed/update-generation-process-status-and-percentage-executed.component';
import UpdateGenerationProcessStatusAndPercentageExecutedService from '@/entities/update-generation-process-status-and-percentage-executed/update-generation-process-status-and-percentage-executed.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('UpdateGenerationProcessStatusAndPercentageExecuted Management Component', () => {
    let wrapper: Wrapper<UpdateGenerationProcessStatusAndPercentageExecutedClass>;
    let comp: UpdateGenerationProcessStatusAndPercentageExecutedClass;
    let updateGenerationProcessStatusAndPercentageExecutedServiceStub: SinonStubbedInstance<UpdateGenerationProcessStatusAndPercentageExecutedService>;

    beforeEach(() => {
      updateGenerationProcessStatusAndPercentageExecutedServiceStub = sinon.createStubInstance<UpdateGenerationProcessStatusAndPercentageExecutedService>(
        UpdateGenerationProcessStatusAndPercentageExecutedService
      );
      updateGenerationProcessStatusAndPercentageExecutedServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<UpdateGenerationProcessStatusAndPercentageExecutedClass>(
        UpdateGenerationProcessStatusAndPercentageExecutedComponent,
        {
          store,
          i18n,
          localVue,
          stubs: { bModal: bModalStub as any },
          provide: {
            updateGenerationProcessStatusAndPercentageExecutedService: () => updateGenerationProcessStatusAndPercentageExecutedServiceStub,
          },
        }
      );
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      updateGenerationProcessStatusAndPercentageExecutedServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllUpdateGenerationProcessStatusAndPercentageExecuteds();
      await comp.$nextTick();

      // THEN
      expect(updateGenerationProcessStatusAndPercentageExecutedServiceStub.retrieve.called).toBeTruthy();
      expect(comp.updateGenerationProcessStatusAndPercentageExecuteds[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      updateGenerationProcessStatusAndPercentageExecutedServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeUpdateGenerationProcessStatusAndPercentageExecuted();
      await comp.$nextTick();

      // THEN
      expect(updateGenerationProcessStatusAndPercentageExecutedServiceStub.delete.called).toBeTruthy();
      expect(updateGenerationProcessStatusAndPercentageExecutedServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
