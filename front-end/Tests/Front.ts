import { expect, test } from "@playwright/test";

//test sign in and sign out
test('test', async ({ page }) => {
    await page.goto('http://localhost:5173/');
    await expect(page.getByRole('button', { name: 'Sign In' })).toBeVisible();
    await page.getByRole('button', { name: 'Sign In' }).click();
    await page.getByPlaceholder('Enter your email address').click();
    await page.getByPlaceholder('Enter your email address').fill('notreal@brown.edu');
    await page.getByRole('button', { name: 'Continue', exact: true }).click();
    await page.getByPlaceholder('Enter your password').fill('notrealnotreal');
    await page.getByRole('button', { name: 'Continue' }).click();
    await expect(page.getByText('Brown Dorm Finder')).toBeVisible();
    await expect(page.getByText('Campus LocationAllFloorsAllPart of SuiteAllPeople in RoomAllHas BathroomAllHas')).toBeVisible();
    await expect(page.getByLabel('Map', { exact: true })).toBeVisible();
    await expect(page.getByText('Press Search to see more resultsThere are matches in the following buildings:')).toBeVisible();
    await expect(page.getByRole('button', { name: 'Sign Out' })).toBeVisible();
    await page.getByRole('button', { name: 'Sign Out' }).click();
    await expect(page.getByRole('button', { name: 'Sign In' })).toBeVisible();
});

//test sign in with non brown account
test('test', async ({ page }) => {
    await page.goto('http://localhost:5173/');
    await expect(page.getByRole('button', { name: 'Sign In' })).toBeVisible();
    await page.getByRole('button', { name: 'Sign In' }).click();
    await page.getByRole('button', { name: 'Sign in with Google Continue' }).click();
    await page.getByLabel('Email or phone').fill('notreal@gmail.com');
    await page.getByRole('button', { name: 'Next' }).click();
    await page.getByRole('heading', { name: 'Couldn’t sign you in' }).locator('span').click();
});

//test pressing on map after signing in gets you dorm information but nothing in right panel
test('test', async ({ page }) => {
    await page.goto('http://localhost:5173/');
    await page.getByRole('button', { name: 'Sign In' }).click();
    await page.getByPlaceholder('Enter your email address').click();
    await page.getByPlaceholder('Enter your email address').fill('notreal@brown.edu');
    await page.getByRole('button', { name: 'Continue', exact: true }).click();
    await page.getByPlaceholder('Enter your password').fill('notrealnotreal');
    await page.getByRole('button', { name: 'Continue' }).click();
    await page.getByLabel('Map', { exact: true }).click({
        position: {
            x: 316,
            y: 32
        }
    });
    await expect(page.getByText('Information Page')).toBeVisible();
    await page.getByText('Miller Hall').click();
    await page.getByText('Nothing Searched Yet').click();
    await page.getByText('There are matches in the').click();
    await page.getByRole('img', { name: 'Miller Hall' }).click();
});

//test multiple dorm building information from search and map, also tests back to map button. 
test('test', async ({ page }) => {
    await page.goto('http://localhost:5173/');
    await page.getByRole('button', { name: 'Sign In' }).click();
    await page.getByPlaceholder('Enter your email address').click();
    await page.getByPlaceholder('Enter your email address').fill('notreal@brown.edu');
    await page.getByRole('button', { name: 'Continue', exact: true }).click();
    await page.getByPlaceholder('Enter your password').fill('notrealnotreal');
    await page.getByRole('button', { name: 'Continue' }).click();
    await expect(page.getByRole('button', { name: 'Search' })).toBeVisible();
    await page.getByRole('button', { name: 'Search' }).click();
    await expect(page.getByText('There are matches in the')).toBeVisible();
    await expect(page.getByRole('button', { name: 'Caswell Hall' })).toBeVisible();
    await page.getByRole('button', { name: 'Caswell Hall' }).click();
    await expect(page.getByText('Information Page')).toBeVisible();
    await expect(page.locator('p').filter({ hasText: /^Caswell Hall$/ })).toBeVisible();
    await expect(page.getByRole('img', { name: 'Caswell Hall' })).toBeVisible();
    await expect(page.getByText('Address is 168 Thayer St,')).toBeVisible();
    await expect(page.getByRole('button', { name: 'Back to Map' })).toBeVisible();
    await page.getByRole('button', { name: 'Back to Map' }).click();
    await expect(page.getByRole('button', { name: 'Graduate Center C' })).toBeVisible();
    await page.getByRole('button', { name: 'Graduate Center C' }).click();
    await expect(page.getByText('Information Page')).toBeVisible();
    await expect(page.locator('p').filter({ hasText: /^Graduate Center C$/ })).toBeVisible();
    await expect(page.getByRole('img', { name: 'Grad Center C' })).toBeVisible();
    await expect(page.getByText('Address is 82 Thayer St,')).toBeVisible();
    await page.getByRole('button', { name: 'Back to Map' }).click();
    await expect(page.getByLabel('Map', { exact: true })).toBeVisible();
    await page.getByLabel('Map', { exact: true }).click({
        position: {
            x: 475,
            y: 531
        }
    });
    await expect(page.getByText('Information Page')).toBeVisible();
    await expect(page.locator('p').filter({ hasText: /^Chapin House$/ })).toBeVisible();
    await expect(page.getByRole('img', { name: 'Chapin House' })).toBeVisible();
    await expect(page.getByText('Address is 116 Thayer St,')).toBeVisible();
    await page.getByRole('button', { name: 'Back to Map' }).click();
    await page.getByLabel('Map', { exact: true }).click({
        position: {
            x: 425,
            y: 458
        }
    });
    await expect(page.getByText('Information Page')).toBeVisible();
    await expect(page.getByText('Sears House', { exact: true })).toBeVisible();
    await expect(page.getByRole('img', { name: 'Sears House' })).toBeVisible();
    await expect(page.getByText('Address is 113 George St,')).toBeVisible();
    await page.getByRole('button', { name: 'Back to Map' }).click();
    await expect(page.getByLabel('Map', { exact: true })).toBeVisible();
    await expect(page.getByText('There are matches in the following buildings:Click here to see results for all')).toBeVisible();
});

//tests settings other than all, tests that dorm rooms come back, for suite, room
test('test', async ({ page }) => {
    await page.goto('http://localhost:5173/');
    await page.getByRole('button', { name: 'Sign In' }).click();
    await page.getByPlaceholder('Enter your email address').click();
    await page.getByPlaceholder('Enter your email address').fill('notreal@brown.edu');
    await page.getByRole('button', { name: 'Continue', exact: true }).click();
    await page.getByPlaceholder('Enter your password').fill('notrealnotreal');
    await page.getByRole('button', { name: 'Continue' }).click();
    await page.getByRole('button', { name: 'Search' }).click();
    await expect(page.getByRole('button', { name: 'Caswell Hall' })).toBeVisible();
    await expect(page.getByRole('button', { name: 'Graduate Center B' })).toBeVisible();
    await expect(page.getByRole('button', { name: 'Chapin House' })).toBeVisible();
    await expect(page.getByRole('button', { name: 'Graduate Center D' })).toBeVisible();
    await expect(page.getByRole('button', { name: 'Diman House' })).toBeVisible();
    await expect(page.getByRole('button', { name: 'Graduate Center A' })).toBeVisible();
    await expect(page.getByRole('button', { name: 'Diman House' })).toBeVisible();
    await expect(page.getByRole('button', { name: 'Graduate Center C' })).toBeVisible();
    await expect(page.getByRole('button', { name: 'Goddard House' })).toBeVisible();
    await expect(page.getByRole('button', { name: 'Buxton House' })).toBeVisible();
    await expect(page.getByRole('button', { name: 'Barbour Hall' })).toBeVisible();
    await page.locator('#CampusLocation svg').nth(2).click();
    await page.getByRole('option', { name: 'Grad Center' }).click();
    await page.getByRole('button', { name: 'Search' }).click();
    await expect(page.getByRole('button', { name: 'Graduate Center A' })).toBeVisible();
    await expect(page.getByRole('button', { name: 'Graduate Center C' })).toBeVisible();
    await expect(page.getByRole('button', { name: 'Graduate Center B' })).toBeVisible();
    await expect(page.getByRole('button', { name: 'Graduate Center D' })).toBeVisible();
    await page.locator('#FloorNumber svg').nth(2).click();
    await page.getByRole('option', { name: 'Floor 5' }).click();
    await page.getByRole('button', { name: 'Search' }).click();
    await page.getByRole('button', { name: 'Graduate Center D' }).click();
    await expect(page.locator('#root')).toContainText('GRADCTR D 520 522');
    await page.locator('#hasBathroom svg').nth(2).click();
    await page.getByRole('option', { name: 'Communal' }).click();
    await page.getByLabel('Remove Grad Center').click();
    await page.locator('#CampusLocation svg').click();
    await page.getByRole('option', { name: 'All' }).click();
    await page.locator('#FloorNumber svg').nth(1).click();
    await page.locator('#FloorNumber svg').click();
    await page.getByRole('option', { name: 'Floor 2' }).click();
    await page.getByRole('button', { name: 'Search' }).click();
    await page.getByRole('button', { name: 'Diman House' }).click();
    await expect(page.locator('#root')).toContainText('DIMAN 201');
    await expect(page.locator('#root')).toContainText('Communal');
    await page.locator('#HasKitchen svg').nth(2).click();
    await page.getByRole('option', { name: 'No' }).click();
    await page.locator('#RoomCapacity > .select__control > .select__indicators > div:nth-child(3)').click();
    await page.getByRole('option', { name: '1' }).click();
    await page.getByRole('button', { name: 'Search' }).click();
    await expect(page.locator('#root')).toContainText('Below are dorms in Diman House that meet your search options');
    await page.getByRole('button', { name: 'Chapin House' }).click();
    await expect(page.getByText('One').first()).toBeVisible();
    await expect(page.getByText('CHAPIN 211')).toBeVisible();
    await expect(page.getByText('No', { exact: true }).nth(1)).toBeVisible();
    await expect(page.getByText('Communal').nth(1)).toBeVisible();
    await page.getByLabel('Remove Floor').click();
    await page.getByLabel('Remove 1').click();
    await page.locator('div').filter({ hasText: /^Communal$/ }).nth(2).click();
    await page.locator('div').filter({ hasText: /^Communal$/ }).nth(1).click();
    await page.getByLabel('Remove Communal').click();
    await page.getByLabel('Remove No').click();
    await page.locator('#PartOfSuite svg').nth(2).click();
    await page.getByRole('option', { name: 'Yes' }).click();
    await page.getByRole('button', { name: 'Search' }).click();
    await page.getByRole('button', { name: 'Barbour Hall' }).click();
    await expect(page.getByText('Suite: BARBOUR 370')).toBeVisible();
    await page.getByText('Room: BARBOUR 370 371').click();
    await expect(page.getByText('Room: BARBOUR 370 374')).toBeVisible();
});

//after pressing something that doesn't match search request, there should be no dorms
test('test', async ({ page }) => {
    await page.goto('http://localhost:5173/');
    await page.getByRole('button', { name: 'Sign In' }).click();
    await page.getByPlaceholder('Enter your email address').click();
    await page.getByPlaceholder('Enter your email address').fill('notreal@brown.edu');
    await page.getByRole('button', { name: 'Continue', exact: true }).click();
    await page.getByPlaceholder('Enter your password').fill('notrealnotreal');
    await page.getByRole('button', { name: 'Continue' }).click();
    await page.locator('#FloorNumber svg').nth(2).click();
    await page.getByRole('option', { name: 'Floor 7' }).click();
    await page.locator('#PartOfSuite svg').nth(2).click();
    await page.getByRole('option', { name: 'Yes' }).click();
    await page.getByRole('button', { name: 'Search' }).click();
    await expect(page.locator('#root')).toContainText('There are matches in the following buildings:');
});

//pressing the "all dorms" button
test('test', async ({ page }) => {
    await page.goto('http://localhost:5173/');
    await page.getByRole('button', { name: 'Sign In' }).click();
    await page.getByPlaceholder('Enter your email address').click();
    await page.getByPlaceholder('Enter your email address').fill('notreal@brown.edu');
    await page.getByRole('button', { name: 'Continue', exact: true }).click();
    await page.getByPlaceholder('Enter your password').fill('notrealnotreal');
    await page.getByRole('button', { name: 'Continue' }).click();
    await page.getByRole('button', { name: 'Search' }).click();
    await page.getByRole('button', { name: 'Click here to see results for' }).click();
    await expect(page.getByLabel('Map', { exact: true })).toBeVisible();
    await expect(page.getByText('CASWELL 207')).toBeVisible();
    await expect(page.getByText('GRADCTR B 140 145')).toBeVisible();
    await expect(page.getByText('GRADCTR B 240 245')).toBeVisible();
    await expect(page.getByText('CASWELL 201')).toBeVisible();
    await expect(page.getByText('CHAPIN 323')).toBeVisible();
    await expect(page.getByText('GODDARD 331')).toBeVisible();
    await page.locator('#CampusLocation path').nth(2).click();
    await page.getByRole('option', { name: 'East Campus' }).click();
    await page.getByRole('button', { name: 'Search' }).click();
    await page.getByRole('button', { name: 'Click here to see results for' }).click();
    await expect(page.getByText('BARBOUR 301')).toBeVisible();
    await expect(page.getByText('BARBOUR 103')).toBeVisible();
    await expect(page.getByText('BARBOUR 305')).toBeVisible();
});

//multi select in drop downs work properly, including "all" option being singular select
//and that that there is always "all" in dropdowns if other options unselected
test('test', async ({ page }) => {
    await page.goto('http://localhost:5173/');
    await page.getByRole('button', { name: 'Sign In' }).click();
    await page.getByPlaceholder('Enter your email address').click();
    await page.getByPlaceholder('Enter your email address').fill('notreal@brown.edu');
    await page.getByRole('button', { name: 'Continue', exact: true }).click();
    await page.getByPlaceholder('Enter your password').fill('notrealnotreal');
    await page.getByRole('button', { name: 'Continue' }).click();
    await expect(page.locator('#root')).toContainText('Campus LocationAll');
    await page.locator('#CampusLocation').getByLabel('Remove All').click();
    await expect(page.locator('#root')).toContainText('Campus LocationAll');
    await page.locator('#CampusLocation svg').nth(2).click();
    await page.getByRole('option', { name: 'Main Green' }).click();
    await expect(page.locator('#root')).toContainText('Campus LocationMain Green');
    await page.locator('#CampusLocation svg').nth(2).click();
    await page.getByRole('option', { name: 'Pembroke' }).click();
    await page.locator('#CampusLocation path').nth(3).click();
    await page.getByRole('option', { name: 'Gregorian Quad' }).click();
    await page.locator('div:nth-child(3) > .css-tj5bde-Svg').first().click();
    await page.getByRole('option', { name: 'Machado House' }).click();
    await page.locator('div:nth-child(3) > .css-tj5bde-Svg').first().click();
    await expect(page.locator('#root')).toContainText('Campus LocationMain GreenPembrokeGrad CenterGregorian QuadMachado House');
    await page.getByLabel('Remove Gregorian Quad').click();
    await page.getByLabel('Remove Grad Center').click();
    await page.getByLabel('Remove Machado House').click();
    await page.getByLabel('Remove Pembroke').click();
    await page.getByLabel('Remove Main Green').click();
    await expect(page.locator('#root')).toContainText('Campus LocationAll');
    await page.locator('#CampusLocation svg').nth(2).click();
    await page.getByRole('option', { name: 'Main Green' }).click();
    await page.locator('#CampusLocation svg').nth(2).click();
    await page.getByRole('option', { name: 'Wriston Quad' }).click();
    await expect(page.locator('#root')).toContainText('Campus LocationMain GreenWriston Quad');
    await page.locator('#CampusLocation svg').nth(2).click();
    await expect(page.locator('#root')).toContainText('Campus LocationAll');
});

//navigation bar is functional
test('test', async ({ page }) => {
    await page.goto('http://localhost:5173/');
    await page.getByRole('button', { name: 'Sign In' }).click();
    await page.getByPlaceholder('Enter your email address').click();
    await page.getByPlaceholder('Enter your email address').fill('notreal@brown.edu');
    await page.getByRole('button', { name: 'Continue', exact: true }).click();
    await page.getByPlaceholder('Enter your password').fill('notrealnotreal');
    await page.getByRole('button', { name: 'Continue' }).click();
    await expect(page.getByRole('link', { name: 'Home', exact: true })).toBeVisible();
    await expect(page.getByRole('link', { name: 'About' })).toBeVisible();
    await page.getByRole('link', { name: 'Contact' }).click();
    await expect(page.getByRole('link', { name: 'Contact' })).toBeVisible();
    await expect(page.getByText('Contact Page')).toBeVisible();
    await expect(page.locator('body')).toBeVisible();
    await page.getByRole('link', { name: 'About' }).click();
    await expect(page.getByRole('heading', { name: 'About Brown Dorm Search' })).toBeVisible();
    await page.getByRole('link', { name: 'Home' }).click();
    await expect(page.getByText('Campus LocationAllFloorsAllPart of SuiteAllPeople in RoomAllHas BathroomAllHas')).toBeVisible();
    await page.getByRole('link', { name: 'Home', exact: true }).click();
    await expect(page.getByText('Campus LocationAllFloorsAllPart of SuiteAllPeople in RoomAllHas BathroomAllHas')).toBeVisible();
    await expect(page.getByText('Brown Dorm Finder')).toBeVisible();
    await page.getByRole('link', { name: 'About' }).click();
    await expect(page.getByText('Brown Dorm Finder')).toBeVisible();
    await page.getByRole('link', { name: 'Contact' }).click();
    await expect(page.getByText('Brown Dorm Finder')).toBeVisible();
});

//scroll room size is functional

