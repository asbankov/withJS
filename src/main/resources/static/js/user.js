const userInfoUrl = '/rest/user'

function getAuthenticationForUserPage() {
    fetch(userInfoUrl)
        .then(response => response.json())
        .then(user => {
            const text = user.username
            const text2 = user.roles.map(r => (r.role=='ROLE_USER'?'USER':'ADMIN'))
            document.getElementById('bold-header').innerHTML = text
            document.getElementById('header-roles').innerHTML = text2
        })
}

getAuthenticationForUserPage()

function getUserInfo() {
    fetch(userInfoUrl)
        .then(response => response.json())
        .then(user => {
            let result = `<tr>
                        <td>${user.id}</td>   
                        <td>${user.firstName}</td>
                        <td>${user.lastName}</td>
                        <td>${user.age}</td>
                        <td>${user.username}</td>
                        <td>${user.roles.map(r => (r.role=='ROLE_USER'?'USER':'ADMIN'))}</td>
                        </tr>`
            document.querySelector('tbody').innerHTML = result
        })
}

getUserInfo()
