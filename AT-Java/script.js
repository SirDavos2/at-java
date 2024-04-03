// Script.js
function deleteSchool() {
    var id = prompt("Digite o ID da escola que deseja deletar:");
    if (id !== null) {
        fetch('/escolas/' + id, {
            method: 'DELETE'
        })
        .then(response => response.text())
        .then(data => {
            alert(data); // Exibe a mensagem de retorno do backend
        })
        .catch(error => {
            console.error('Erro:', error);
        });
    }
}

function deleteProfessor() {
    var id = prompt("Digite o ID do professor que deseja deletar:");
    if (id !== null) {
        fetch('/professores/' + id, {
            method: 'DELETE'
        })
        .then(response => response.text())
        .then(data => {
            alert(data); // Exibe a mensagem de retorno do backend
        })
        .catch(error => {
            console.error('Erro:', error);
        });
    }
}
