import com.example.managementuser.ManagementUserApplication;
import com.example.managementuser.dto.JoinDTO;
import com.example.managementuser.entity.UserEntity;
import com.example.managementuser.entity.UserHistoryEntity;
import com.example.managementuser.repository.UserHistoryRepository;
import com.example.managementuser.repository.UserRepository;
import com.example.managementuser.service.JoinService;
import com.example.managementuser.type.ActionType;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@SpringBootTest(classes = ManagementUserApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ManagementUserTest {

    @Test
    void test() {
        int a = 5;
        int b = 7;
        int expectedSum = 12;
        int actualSum = a + b;
        Assertions.assertEquals(expectedSum, actualSum);
    }


    @Mock
    private UserRepository userRepository;

    @Mock
    private UserHistoryRepository userHistoryRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private JoinService joinService;

    @Test
    public void testJoinProcess_UserDoesNotExist() {
        when(userRepository.existsByUsername(joinDTO.getUsername())).thenReturn(false);
        when(bCryptPasswordEncoder.encode(joinDTO.getPassword())).thenReturn("encodedPass");

        joinService.joinProcess(joinDTO, userHistoryEntity);

        verify(userRepository, times(1)).save(any(UserEntity.class));
        verify(userHistoryRepository, times(1)).save(any(UserHistoryEntity.class));

        assertEquals(ActionType.C, userHistoryEntity.getActionType());
    }

    private JoinDTO joinDTO;

    private UserHistoryEntity userHistoryEntity;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        joinDTO = new JoinDTO();
        joinDTO.setUsername("testUser1");
        joinDTO.setPassword("testPass1");
        joinDTO.setName("TestName1");

        userHistoryEntity = new UserHistoryEntity();
    }

    @Test
    public void testJoinProcess_UserExists() {
        when(userRepository.existsByUsername(joinDTO.getUsername())).thenReturn(true);

        joinService.joinProcess(joinDTO, userHistoryEntity);

        verify(userRepository, never()).save(any(UserEntity.class));
        verify(userHistoryRepository, never()).save(any(UserHistoryEntity.class));
    }


}


