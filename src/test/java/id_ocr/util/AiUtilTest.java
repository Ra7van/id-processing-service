package id_ocr.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.chat.client.ChatClient;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AiUtilTest {
    @Mock
    private ChatClient.Builder chatClientBuilder;
    
    @Mock
    private ChatClient chatClient;

    @InjectMocks
    private AiUtil aiUtil;

    @BeforeEach
    void setUp() {
        aiUtil = new AiUtil();
    }

    @Test
    void shouldBuildChatClientTest() {
        when(chatClientBuilder.build()).thenReturn(chatClient);

        ChatClient actualResult = aiUtil.chatClient(chatClientBuilder);

        assertSame(chatClient, actualResult);
        verify(chatClientBuilder).build();
    }
}
