/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import RoutineDetailComponent from '@/entities/routine/routine-details.vue';
import RoutineClass from '@/entities/routine/routine-details.component';
import RoutineService from '@/entities/routine/routine.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Routine Management Detail Component', () => {
    let wrapper: Wrapper<RoutineClass>;
    let comp: RoutineClass;
    let routineServiceStub: SinonStubbedInstance<RoutineService>;

    beforeEach(() => {
      routineServiceStub = sinon.createStubInstance<RoutineService>(RoutineService);

      wrapper = shallowMount<RoutineClass>(RoutineDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { routineService: () => routineServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundRoutine = { id: 'ABC' };
        routineServiceStub.find.resolves(foundRoutine);

        // WHEN
        comp.retrieveRoutine('ABC');
        await comp.$nextTick();

        // THEN
        expect(comp.routine).toBe(foundRoutine);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundRoutine = { id: 'ABC' };
        routineServiceStub.find.resolves(foundRoutine);

        // WHEN
        comp.beforeRouteEnter({ params: { routineId: 'ABC' } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.routine).toBe(foundRoutine);
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
