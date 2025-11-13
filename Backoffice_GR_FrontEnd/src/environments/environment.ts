export const environment = {
  production: false,
  pathApi: "https://localhost:8443/",
    "uriConfig": {
        "clientId": "bbc8b081-102d-4c06-a274-34dc11cb9292",
        "path_iang": "https://auth03coll.giustizia.it/b2cmingiustiziaspidcoll.onmicrosoft.com/b2c_1a_signin_aad_spid/oauth2/v2.0/authorize",
        "path_iang_logout": "https://auth03coll.giustizia.it/b2cmingiustiziaspidcoll.onmicrosoft.com/b2c_1a_signin_aad_spid/oauth2/v2.0/authorize",
        "logout_redirect_uri": "https://localhost:4400/logout",
        "scope": "https://b2cmingiustiziaspidcoll.onmicrosoft.com/GiustiziaAPI/Mail.Send openid profile offline_access",
        "redirectUri": "https://localhost:4400/",
        "response_mode": "fragment",
        "response_type": "code",
        "code_challenge": "ab907ecef2e9006f734b96e3b9d907212e41c6924c56a54f688ae433",
        "code_challenge_method": "plain"
    },
    "uriLogout": "https://auth03coll.giustizia.it/b2cmingiustiziaspidcoll.onmicrosoft.com/b2c_1a_signin_aad_spid/oauth2/v2.0/logout?post_logout_redirect_uri=https://localhost:4400/logout",
    "rowsTable": 20
};
