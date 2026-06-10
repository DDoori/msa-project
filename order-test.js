import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
    vus: 100,
    iterations: 10000,
};

const BASE_URL = 'http://localhost:8080';
const PRODUCT_ID = 'f8454217-c581-4bd8-8b5c-1b1a3fa6faff';

export function setup() {
    const res = http.post(`${BASE_URL}/users/login`, JSON.stringify({
        email: 'receiver@test.com',
        password: '1234',
    }), {
        headers: { 'Content-Type': 'application/json' },
    });
    return { token: res.body };
}

export default function (data) {
    const params = {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${data.token}`,
        },
    };

    const res = http.post(`${BASE_URL}/orders`, JSON.stringify({
        productId: PRODUCT_ID,
        quantity: 1,
    }), params);

    check(res, {
        'status is 201': (r) => r.status === 201,
    });

    sleep(0.1);
}