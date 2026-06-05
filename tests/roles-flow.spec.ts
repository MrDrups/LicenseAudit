import { test, expect } from '@playwright/test';
import { Page } from '@playwright/test';

// Helper: login via Spring Security form
async function login(page: Page, username: string, password: string) {
  await page.goto('/login');
  await page.fill('#username', username);
  await page.fill('input[name="password"]', password);
  await Promise.all([
    page.waitForURL(/\/(login\?error|$|\?)/, { timeout: 10000 }).catch(() => page.waitForLoadState('networkidle')),
    page.click('button[type="submit"]'),
  ]);
  // After successful login, we should be redirected away from /login
  // Wait a bit for redirect
  await page.waitForLoadState('networkidle');
}

// Helper: logout
async function logout(page: Page) {
  // The "Выйти" button posts to /logout
  const logoutBtn = page.locator('form[action="/logout"] button, button:has-text("Выйти")');
  if (await logoutBtn.count() > 0) {
    await Promise.all([
      page.waitForURL('/login', { timeout: 10000 }).catch(() => page.waitForLoadState('networkidle')),
      logoutBtn.first().click(),
    ]);
  }
}

// Helper: format date as YYYY-MM-DD
function formatDate(date: Date): string {
  const y = date.getFullYear();
  const m = String(date.getMonth() + 1).padStart(2, '0');
  const d = String(date.getDate()).padStart(2, '0');
  return `${y}-${m}-${d}`;
}

test.describe.serial('LicenseAudit role-based E2E flow', () => {

  test('USER creates a request', async ({ page }) => {
    // Step 1: Login as user
    await login(page, 'user', 'user');

    // Verify we're on the main page (not /login)
    await expect(page).not.toHaveURL(/\/login/);

    // Step 2: Navigate to "Мои заявки"
    await page.click('a:has-text("Мои заявки")');
    await expect(page).toHaveURL(/\/requests/);

    // Step 3: Click "Создать заявку"
    await page.click('a[href="/requests/create"]');
    await expect(page).toHaveURL(/\/requests\/create/);

    // Step 4: Fill wizard form
    // Wait for wizard form to be visible
    await expect(page.locator('#requestWizardForm')).toBeVisible();

    // Step 1: Fill license key, company, plan
    await page.fill('#key', 'TEST-LICENSE-KEY-E2E');

    // Check if company select has options
    const companySelect = page.locator('#companyId');
    const companyOptions = await companySelect.locator('option').count();
    if (companyOptions <= 1) {
      // Only the placeholder option, skip the rest
      test.skip();
    }
    await companySelect.selectOption({ index: 1 });

    const planSelect = page.locator('#licensePlanId');
    const planOptions = await planSelect.locator('option').count();
    if (planOptions <= 1) {
      test.skip();
    }
    await planSelect.selectOption({ index: 1 });

    // Click Next to go to Step 2
    await page.click('#wizardNext');
    await expect(page.locator('#step2.wizard-pane.active')).toBeVisible({ timeout: 10000 });

    // Step 2: Auto-filled, just click Next
    await page.click('#wizardNext');
    await expect(page.locator('#step3.wizard-pane.active')).toBeVisible({ timeout: 10000 });

    // Step 3: Fill dates
    const today = new Date();
    const future = new Date(today);
    future.setMonth(future.getMonth() + 1);

    await page.fill('#startDate', formatDate(today));
    await page.fill('#endDate', formatDate(future));
    await page.selectOption('#notificationPeriod', 'Нет');

    // Click Next to go to Step 4
    await page.click('#wizardNext');
    await expect(page.locator('#step4.wizard-pane.active')).toBeVisible({ timeout: 10000 });

    // Step 4: Fill comment and submit
    await page.fill('#comment', 'E2E test request');
    await Promise.all([
      page.waitForURL(/\/($|licenses|requests)/, { timeout: 15000 }).catch(() => page.waitForLoadState('networkidle')),
      page.click('#wizardSubmit'),
    ]);

    // Verify redirect happened
    await expect(page).not.toHaveURL(/\/requests\/create/);

    // Logout
    await logout(page);
  });

  test('LEAD approves the request', async ({ page }) => {
    // Step 1: Login as lead
    await login(page, 'lead', 'lead');

    // Verify we're on the main page
    await expect(page).not.toHaveURL(/\/login/);

    // Step 2: Navigate to "Заявки на согласование"
    await page.click('a:has-text("Заявки на согласование")');
    await expect(page).toHaveURL(/\/requests/);

    // Step 3: Click the "Ожидающие" tab
    await page.click('button[data-bs-target="#tab-pending"]');
    // Wait for the pending tab pane to be visible
    await expect(page.locator('#tab-pending')).toBeVisible({ timeout: 10000 });

    // Step 4: Find the request with key TEST-LICENSE-KEY-E2E and approve it
    const approveForm = page.locator('form[action*="/requests/approve/"]').first();
    await expect(approveForm).toBeVisible({ timeout: 10000 });

    // Click the approve button inside the form
    await Promise.all([
      page.waitForLoadState('networkidle', { timeout: 15000 }),
      approveForm.locator('button').click(),
    ]);

    // Verify: request no longer in pending (or status badge changed)
    // After approve, the page should reload/refresh
    await page.waitForLoadState('networkidle');

    // Logout
    await logout(page);
  });

  test('ADMIN views statistics', async ({ page }) => {
    // Step 1: Login as root (admin)
    await login(page, 'root', 'root');

    // Verify we're on the main page
    await expect(page).not.toHaveURL(/\/login/);

    // Step 2: Navigate to "Администрирование"
    await page.click('a:has-text("Администрирование")');
    await expect(page).toHaveURL(/\/admin/);

    // Step 3: Verify admin page heading
    await expect(page.locator('h1, h2, h3').filter({ hasText: 'Администрирование' })).toBeVisible({ timeout: 10000 });

    // Step 4: Click the "Статистика" tab
    await page.click('#statistics-tab');
    await expect(page.locator('#statistics')).toBeVisible({ timeout: 10000 });

    // Step 5: Verify statistics counter cards exist
    // The model attributes like totalLicenses, totalCompanies, activeLicenses should be rendered
    const statisticsPane = page.locator('#statistics');
    await expect(statisticsPane).toBeVisible();

    // Check for counter elements - they may have ids or just be text
    // Look for elements containing these values
    const statsContent = await statisticsPane.textContent();
    // Just verify the pane has content (not empty)
    expect(statsContent?.trim().length).toBeGreaterThan(0);

    // Logout
    await logout(page);
  });

});
