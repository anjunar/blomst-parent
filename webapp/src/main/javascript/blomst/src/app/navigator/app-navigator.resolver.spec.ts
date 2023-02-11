import { TestBed } from '@angular/core/testing';

import { AppNavigatorResolver } from './app-navigator.resolver';

describe('AppNavigatorResolver', () => {
  let resolver: AppNavigatorResolver;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    resolver = TestBed.inject(AppNavigatorResolver);
  });

  it('should be created', () => {
    expect(resolver).toBeTruthy();
  });
});
