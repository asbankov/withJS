const userInfoUrl = '/rest/user'
const allUsersUrl = '/rest/users'
const allRolesUrl = '/rest/roles'
const sendNewUrl = '/rest/new'
const deleteUrl = '/rest/delete'
const idRolesUrl = '/rest/idroles'
const editUrl = '/rest/edit'
let result=''
let anotherResult = ''
let ananResult=''

const addFirstName = document.getElementById("firstName")
const addLastName = document.getElementById("lastName")
const addAge = document.getElementById("age")
const addUsername = document.getElementById("username")
const addPassword = document.getElementById("password")

const usersTable = document.querySelector('#nav-tab a:first-child')
const newUsersTable = bootstrap.Tab.getOrCreateInstance(usersTable)

const deleteModal= document.getElementById('deleteModal')
const deleteId = document.getElementById('DId')
const deleteFN = document.getElementById('DFirstName')
const deleteLN = document.getElementById('DLastName')
const deleteAge = document.getElementById('DAge')
const deleteUsername = document.getElementById('DUsername')
const deleteRoles = document.getElementById('DRoles')

const newDeleteModal = bootstrap.Modal.getOrCreateInstance(deleteModal)

const editId = document.getElementById('EId')
const editFN = document.getElementById('EFirstName')
const editLN = document.getElementById('ELastName')
const editAge = document.getElementById('EAge')
const editUsername = document.getElementById('EUsername')
const editRoles = document.getElementById('ERoles')
const editPassword = document.getElementById('EPassword')

const editModal = document.getElementById('editModal')
const newEditModal = bootstrap.Modal.getOrCreateInstance(editModal)

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

function getAllUsers() {
    result=''
    fetch(allUsersUrl)
        .then(response => response.json())
        .then(users => {users.forEach(user => {
            result +=
                `<tr>
                    <td>${user.id}</td>
                    <td>${user.firstName}</td>
                    <td>${user.lastName}</td>
                    <td>${user.age}</td>
                    <td>${user.username}</td>
                    <td>
                       ${user.roles.map(r => (r.role=='ROLE_USER'?'USER':'ADMIN'))}
                    </td>
                    <td>
                        <button type="submit" class="btnEdit btn btn-sm btn-primary text-white" data-bs-toggle="modal"
                           data-bs-target="#editModal">Edit</button>
                    </td>
                    <td>
                        <button type="submit" class="btnDel btn btn-sm btn-danger text-white" data-bs-toggle="modal"
                           data-bs-target="#deleteModal">Delete</button>
                    </td>
                </tr>`
        })
            document.querySelector('tbody').innerHTML = result
})}

getAllUsers()

function addRoleOptions (target) {
    anotherResult=''
    fetch(allRolesUrl).then(response => response.json())
        .then(roles => {roles.forEach(role => {
            anotherResult += `<option value="${role.id}"> ${role.role=='ROLE_USER'?'USER':'ADMIN'} </option>`
        })
        target.innerHTML = anotherResult
        })
}

function addIdRoleOptions (id, target) {
    ananResult=''
    let tmp = idRolesUrl + `/${id}`
    fetch(idRolesUrl + `/${id}`).then(response => response.json())
        .then(roles => {roles.forEach(role => {
            ananResult += `<option value="${role.id}"> ${role.role=='ROLE_USER'?'USER':'ADMIN'} </option>`
        })
            target.innerHTML = ananResult
        })
}

addRoleOptions(document.getElementById("roleOptions"))

function roleArray(target) {
    let array = []
    for(let i = 0; i < target.length; i++) {
        if(target[i].selected) {
            let role = {id: target[i].value}
            array.push(role)
        }
    }
    return array
}

const refreshUsersTable = () => {
    fetch(allUsersUrl)
        .then(response => response.json())
        .then(data => {
            result = ''
            getAllUsers(data)
        })
}

document.getElementById('newUser').addEventListener('submit', (event) => {
    event.preventDefault()
    let roles = roleArray(document.getElementById("roleOptions"))
    fetch(sendNewUrl, {
        method: 'POST', headers: {'Content-Type': 'application/json'}, body: JSON.stringify({
            firstName: addFirstName.value,
            lastName: addLastName.value,
            age: addAge.value,
            username: addUsername.value,
            password: addPassword.value,
            roles: roles
        })
    }) .then(data => data.json())
        .catch(error => console.log(error))
        .then(getAllUsers)


    addFirstName.value = ''
    addLastName.value=''
    addAge.value=''
    addUsername.value=''
    addPassword.value=''
    document.getElementById("roleOptions").value=''

    newUsersTable.show()
    //alert('I am here')
    })


const on = (element, event, selector, handler) => {
    element.addEventListener(event, e => {
        if (e.target.closest(selector)) {
            handler(e)
        }
    })
}

on(document, 'click', '.btnDel', e => {
    let target = e.target.parentNode.parentNode
    id = target.children[0].innerHTML
    deleteId.value = target.children[0].innerHTML
    deleteFN.value = target.children[1].innerHTML
    deleteLN.value = target.children[2].innerHTML
    deleteAge.value = target.children[3].innerHTML
    deleteUsername.value = target.children[4].innerHTML
    deleteRoles.value = addIdRoleOptions(id, deleteRoles)
})


deleteModal.addEventListener('submit', (e) => {
    e.preventDefault()
    fetch(deleteUrl + `/${id}`, {
        method: 'DELETE',
    })
        .then(data => data.json())
        .catch(error => console.log(error))
        .then(getAllUsers)
    newDeleteModal.hide()

})



on(document, 'click', '.btnEdit', e => {
    let target = e.target.parentNode.parentNode
    id = target.children[0].innerHTML
    editId.value = target.children[0].innerHTML
    editFN.value = target.children[1].innerHTML
    editLN.value = target.children[2].innerHTML
    editAge.value = target.children[3].innerHTML
    editUsername.value = target.children[4].innerHTML
    editPassword.value = target.children[5].innerHTML
    editRoles.value = addRoleOptions(editRoles)
})


editModal.addEventListener('submit', (e) => {
    e.preventDefault()
    let options = document.querySelector('#ERoles');
    let setRoles = roleArray(options)
    fetch( editUrl, {
        method: 'POST', headers: {'Content-Type': 'application/json'}, body: JSON.stringify({
            id: editId.value,
            firstName: editFN.value,
            lastName: editLN.value,
            age: editAge.value,
            username: editUsername.value,
            password: editPassword.value,
            roles: setRoles
        })
    })
        .then(data => data.json())
        .catch(error => console.log(error))
        .then(getAllUsers)
    newEditModal.hide()
})
