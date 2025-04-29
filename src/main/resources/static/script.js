function openTab(tabName) {
    const tabContents = document.getElementsByClassName("tab-content");
    for (let i = 0; i < tabContents.length; i++) {
        tabContents[i].style.display = "none";
    }

    const tabButtons = document.getElementsByClassName("tab-btn");
    for (let i = 0; i < tabButtons.length; i++) {
        tabButtons[i].classList.remove("active");
    }

    document.getElementById(tabName).style.display = "block";
    event.currentTarget.classList.add("active");
}

function loadRequestCount() {
    fetch('/metrics/count')
        .then(response => response.json())
        .then(count => {
            document.getElementById('requestCount').textContent = `Requests: ${count}`;
        });
}

function resetCounter() {
    fetch('/metrics/count/reset')
        .then(() => {
            loadRequestCount();
        });
}

function loadStudentGroups() {
    fetch('/student-groups')
        .then(response => response.json())
        .then(groups => {
            const tableBody = document.querySelector('#groupsTable tbody');
            tableBody.innerHTML = '';

            groups.forEach(group => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${group.id}</td>
                    <td>${group.name}</td>
                    <td>${group.specialityName}</td>
                    <td>
                        <button class="action-btn edit-btn" onclick="editStudentGroup(${group.id})">Edit</button>
                        <button class="action-btn delete-btn" onclick="deleteStudentGroup(${group.id})">Delete</button>
                        <button class="action-btn" onclick="showGroupSchedule(${group.id})">View Schedule</button>
                    </td>
                `;
                tableBody.appendChild(row);
            });

            const groupSelect = document.getElementById('groupSelect');
            groupSelect.innerHTML = '';
            groups.forEach(group => {
                const option = document.createElement('option');
                option.value = group.id;
                option.textContent = `${group.name} (${group.specialityName})`;
                groupSelect.appendChild(option);
            });
        });
}

function addStudentGroup() {
    const name = document.getElementById('groupName').value;
    const speciality = document.getElementById('specialityName').value;

    if (!name || !speciality) {
        alert('Please fill all fields');
        return;
    }

    fetch('/student-groups', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            name: name,
            specialityName: speciality
        })
    })
        .then(response => response.json())
        .then(() => {
            loadStudentGroups();
            document.getElementById('groupName').value = '';
            document.getElementById('specialityName').value = '';
            loadRequestCount();
        });
}

function editStudentGroup(id) {
    if (!id || isNaN(id)) {
        alert('Invalid group ID');
        return;
    }

    fetch(`/student-groups/${id}`)
        .then(response => response.json())
        .then(group => {
            document.getElementById('groupName').value = group.name;
            document.getElementById('specialityName').value = group.specialityName;

            const addButton = document.querySelector('#student-groups .form-group button');
            addButton.textContent = 'Update';
            addButton.onclick = function() { updateStudentGroup(id); };
        });
}

function updateStudentGroup(id) {
    const name = document.getElementById('groupName').value;
    const speciality = document.getElementById('specialityName').value;

    if (!name || !speciality) {
        alert('Please fill all fields');
        return;
    }

    fetch(`/student-groups/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            id: id,
            name: name,
            specialityName: speciality
        })
    })
        .then(response => response.json())
        .then(() => {
            loadStudentGroups();
            document.getElementById('groupName').value = '';
            document.getElementById('specialityName').value = '';

            const addButton = document.querySelector('#student-groups .form-group button');
            addButton.textContent = 'Add Group';
            addButton.onclick = function() { addStudentGroup(); };
            loadRequestCount();
        });
}

function deleteStudentGroup(id) {
    if (!id || isNaN(id)) {
        alert('Invalid group ID');
        return;
    }

    if (confirm('Are you sure you want to delete this group?')) {
        fetch(`/student-groups/${id}`, {
            method: 'DELETE'
        })
            .then(() => {
                loadStudentGroups();
                loadRequestCount();
            });
    }
}

function loadSchedules() {
    fetch('/schedules')
        .then(response => response.json())
        .then(schedules => {
            console.log('Loaded schedules:', schedules);
            const tableBody = document.querySelector('#schedulesTable tbody');
            tableBody.innerHTML = '';

            schedules.forEach(schedule => {
                const groups = schedule.studentGroups && schedule.studentGroups.length > 0
                    ? schedule.studentGroups.map(g => g.name).join(', ')
                    : 'No groups';
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${schedule.id}</td>
                    <td>${schedule.startLessonTime}</td>
                    <td>${schedule.endLessonTime}</td>
                    <td>${schedule.lessonTypeAbbrev}</td>
                    <td>${schedule.subjectFullName}</td>
                    <td>${groups}</td>
                    <td>
                        <button class="action-btn edit-btn" onclick="editSchedule(${schedule.id})">Edit</button>
                        <button class="action-btn delete-btn" onclick="deleteSchedule(${schedule.id})">Delete</button>
                    </td>
                `;
                tableBody.appendChild(row);
            });
        })
        .catch(error => console.error('Error loading schedules:', error));
}

function addSchedule() {
    const startTime = document.getElementById('startTime').value;
    const endTime = document.getElementById('endTime').value;
    const lessonType = document.getElementById('lessonType').value;
    const subjectName = document.getElementById('subjectName').value;
    const groupSelect = document.getElementById('groupSelect');
    const selectedGroups = Array.from(groupSelect.selectedOptions).map(option => ({
        id: parseInt(option.value),
        name: option.textContent.split(' (')[0]
    }));

    if (!startTime || !endTime || !lessonType || !subjectName) {
        alert('Please fill all fields');
        return;
    }

    const scheduleData = {
        startLessonTime: startTime,
        endLessonTime: endTime,
        lessonTypeAbbrev: lessonType,
        subjectFullName: subjectName,
        studentGroups: selectedGroups
    };

    console.log('Sending schedule data:', scheduleData);

    fetch('/schedules', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(scheduleData)
    })
        .then(response => response.json())
        .then(data => {
            console.log('Schedule added:', data);
            loadSchedules();
            document.getElementById('startTime').value = '';
            document.getElementById('endTime').value = '';
            document.getElementById('lessonType').value = '';
            document.getElementById('subjectName').value = '';
            groupSelect.selectedIndex = -1;
            loadRequestCount();
        })
        .catch(error => console.error('Error adding schedule:', error));
}

function editSchedule(id) {
    if (!id || isNaN(id)) {
        alert('Invalid schedule ID');
        return;
    }

    fetch(`/schedules/${id}`)
        .then(response => response.json())
        .then(schedule => {
            console.log('Editing schedule:', schedule);
            document.getElementById('startTime').value = schedule.startLessonTime;
            document.getElementById('endTime').value = schedule.endLessonTime;
            document.getElementById('lessonType').value = schedule.lessonTypeAbbrev;
            document.getElementById('subjectName').value = schedule.subjectFullName;

            const groupSelect = document.getElementById('groupSelect');
            Array.from(groupSelect.options).forEach(option => {
                option.selected = schedule.studentGroups.some(g => g.id === parseInt(option.value));
            });

            const addButton = document.querySelector('#schedules .form-group button');
            addButton.textContent = 'Update';
            addButton.onclick = function() { updateSchedule(id); };
        })
        .catch(error => console.error('Error fetching schedule for edit:', error));
}

function updateSchedule(id) {
    const startTime = document.getElementById('startTime').value;
    const endTime = document.getElementById('endTime').value;
    const lessonType = document.getElementById('lessonType').value;
    const subjectName = document.getElementById('subjectName').value;
    const groupSelect = document.getElementById('groupSelect');
    const selectedGroups = Array.from(groupSelect.selectedOptions).map(option => ({
        id: parseInt(option.value),
        name: option.textContent.split(' (')[0]
    }));

    if (!startTime || !endTime || !lessonType || !subjectName) {
        alert('Please fill all fields');
        return;
    }

    const scheduleData = {
        startLessonTime: startTime,
        endLessonTime: endTime,
        lessonTypeAbbrev: lessonType,
        subjectFullName: subjectName,
        studentGroups: selectedGroups
    };

    console.log('Updating schedule data:', scheduleData);

    fetch(`/schedules/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(scheduleData)
    })
        .then(response => response.json())
        .then(data => {
            console.log('Schedule updated:', data);
            loadSchedules();
            document.getElementById('startTime').value = '';
            document.getElementById('endTime').value = '';
            document.getElementById('lessonType').value = '';
            document.getElementById('subjectName').value = '';
            groupSelect.selectedIndex = -1;

            const addButton = document.querySelector('#schedules .form-group button');
            addButton.textContent = 'Add Schedule';
            addButton.onclick = function() { addSchedule(); };
            loadRequestCount();
        })
        .catch(error => console.error('Error updating schedule:', error));
}

function deleteSchedule(id) {
    if (!id || isNaN(id)) {
        alert('Invalid schedule ID');
        return;
    }

    if (confirm('Are you sure you want to delete this schedule?')) {
        fetch(`/schedules/${id}`, {
            method: 'DELETE'
        })
            .then(() => {
                loadSchedules();
                loadRequestCount();
            })
            .catch(error => console.error('Error deleting schedule:', error));
    }
}

function fetchApiSchedule() {
    const group = document.getElementById('apiGroup').value;
    const dateInput = document.getElementById('apiDate').value;

    if (!group || !dateInput) {
        alert('Please fill all fields');
        return;
    }

    const date = new Date(dateInput);
    const formattedDate = date.toISOString().split('T')[0];

    fetch(`/api/schedule?group=${group}&date=${formattedDate}`)
        .then(response => response.json())
        .then(schedules => {
            const resultsDiv = document.getElementById('apiResults');
            resultsDiv.innerHTML = '';

            if (schedules.length === 0) {
                resultsDiv.innerHTML = '<p>No schedules found for this date.</p>';
                return;
            }

            schedules.forEach(schedule => {
                const scheduleDiv = document.createElement('div');
                scheduleDiv.className = 'schedule-item';

                const groups = schedule.studentGroups.map(g => g.name).join(', ');
                const teachers = schedule.employees.map(e =>
                    `${e.lastName} ${e.firstName.charAt(0)}.${e.middleName ? ' ' + e.middleName.charAt(0) + '.' : ''}`
                ).join(', ');

                scheduleDiv.innerHTML = `
                    <h3>${schedule.subjectFullName}</h3>
                    <p><strong>Time:</strong> ${schedule.startLessonTime} - ${schedule.endLessonTime}</p>
                    <p><strong>Type:</strong> ${schedule.lessonTypeAbbrev}</p>
                    <p><strong>Groups:</strong> ${groups}</p>
                    <p><strong>Teachers:</strong> ${teachers}</p>
                    <p><strong>Classrooms:</strong> ${schedule.auditories.join(', ')}</p>
                `;

                resultsDiv.appendChild(scheduleDiv);
            });
            loadRequestCount();
        })
        .catch(error => console.error('Error fetching API schedule:', error));
}

function showGroupSchedule(groupId) {
    if (!groupId || isNaN(groupId)) {
        alert('Invalid group ID');
        return;
    }

    fetch(`/schedules/by-group/${groupId}`)
        .then(response => response.json())
        .then(schedules => {
            console.log('Schedules for group', groupId, ':', schedules);
            const modal = document.createElement('div');
            modal.className = 'modal';

            let modalContent = '<div class="modal-content"><span class="close">×</span><h2>Group Schedule</h2>';

            if (schedules.length === 0) {
                modalContent += '<p>No schedules found for this group.</p>';
            } else {
                modalContent += '<table><thead><tr><th>Start Time</th><th>End Time</th><th>Subject</th><th>Type</th></tr></thead><tbody>';

                schedules.forEach(schedule => {
                    modalContent += `
                        <tr>
                            <td>${schedule.startLessonTime}</td>
                            <td>${schedule.endLessonTime}</td>
                            <td>${schedule.subjectFullName}</td>
                            <td>${schedule.lessonTypeAbbrev}</td>
                        </tr>
                    `;
                });

                modalContent += '</tbody></table>';
            }

            modalContent += '</div>';
            modal.innerHTML = modalContent;

            document.body.appendChild(modal);

            modal.querySelector('.close').onclick = function() {
                document.body.removeChild(modal);
            };

            window.onclick = function(event) {
                if (event.target === modal) {
                    document.body.removeChild(modal);
                }
            };
        })
        .catch(error => console.error('Error fetching group schedule:', error));
}

document.addEventListener('DOMContentLoaded', function() {
    loadRequestCount();
    loadStudentGroups();
    loadSchedules();

    const today = new Date();
    document.getElementById('apiDate').valueAsDate = today;
});