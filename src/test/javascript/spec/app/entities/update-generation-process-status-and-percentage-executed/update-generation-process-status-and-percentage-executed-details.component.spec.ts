/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import UpdateGenerationProcessStatusAndPercentageExecutedDetailComponent from '@/entities/update-generation-process-status-and-percentage-executed/update-generation-process-status-and-percentage-executed-details.vue';
import UpdateGenerationProcessStatusAndPercentageExecutedClass from '@/entities/update-generation-process-status-and-percentage-executed/update-generation-process-status-and-percentage-executed-details.component';
import UpdateGenerationProcessStatusAndPercentageExecutedService from '@/entities/update-generation-process-status-and-percentage-executed/update-generation-process-status-and-percentage-executed.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('UpdateGenerationProcessStatusAndPercentageExecuted Management Detail Component', () => {
    let wrapper: Wrapper<UpdateGenerationProcessStatusAndPercentageExecutedClass>;
    let comp: UpdateGenerationProcessStatusAndPercentageExecutedClass;
    let updateGenerationProcessStatusAndPercentageExecutedServiceStub: SinonStubbedInstance<UpdateGenerationProcessStatusAndPercentageExecutedService>;

    beforeEach(() => {
      updateGenerationProcessStatusAndPercentageExecutedServiceStub = sinon.createStubInstance<UpdateGenerationProcessStatusAndPercentageExecutedService>(
        UpdateGenerationProcessStatusAndPercentageExecutedService
      );

      wrapper = shallowMount<UpdateGenerationProcessStatusAndPercentageExecutedClass>(
        UpdateGenerationProcessStatusAndPercentageExecutedDetailComponent,
        {
          store,
          i18n,
          localVue,
          router,
          provide: {
            updateGenerationProcessStatusAndPercentageExecutedService: () => updateGenerationProcessStatusAndPercentageExecutedServiceStub,
          },
        }
      );
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundUpdateGenerationProcessStatusAndPercentageExecuted = { id: 123 };
        updateGenerationProcessStatusAndPercentageExecutedServiceStub.find.resolves(
          foundUpdateGenerationProcessStatusAndPercentageExecuted
        );

        // WHEN
        comp.retrieveUpdateGenerationProcessStatusAndPercentageExecuted(123);
        await comp.$nextTick();

        // THEN
        expect(comp.updateGenerationProcessStatusAndPercentageExecuted).toBe(foundUpdateGenerationProcessStatusAndPercentageExecuted);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundUpdateGenerationProcessStatusAndPercentageExecuted = { id: 123 };
        updateGenerationProcessStatusAndPercentageExecutedServiceStub.find.resolves(
          foundUpdateGenerationProcessStatusAndPercentageExecuted
        );

        // WHEN
        comp.beforeRouteEnter({ params: { updateGenerationProcessStatusAndPercentageExecutedId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.updateGenerationProcessStatusAndPercentageExecuted).toBe(foundUpdateGenerationProcessStatusAndPercentageExecuted);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
