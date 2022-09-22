/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import ProcessDetailComponent from '@/entities/process/process-details.vue';
import ProcessClass from '@/entities/process/process-details.component';
import ProcessService from '@/entities/process/process.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Process Management Detail Component', () => {
    let wrapper: Wrapper<ProcessClass>;
    let comp: ProcessClass;
    let processServiceStub: SinonStubbedInstance<ProcessService>;

    beforeEach(() => {
      processServiceStub = sinon.createStubInstance<ProcessService>(ProcessService);

      wrapper = shallowMount<ProcessClass>(ProcessDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { processService: () => processServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundProcess = { id: 123 };
        processServiceStub.find.resolves(foundProcess);

        // WHEN
        comp.retrieveProcess(123);
        await comp.$nextTick();

        // THEN
        expect(comp.process).toBe(foundProcess);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundProcess = { id: 123 };
        processServiceStub.find.resolves(foundProcess);

        // WHEN
        comp.beforeRouteEnter({ params: { processId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.process).toBe(foundProcess);
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
