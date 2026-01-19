import "./css/ConfirmDialog.css";

interface ConfirmDialogProps {
  open: boolean;
  title?: string;
  message: string;
  onConfirm: () => void;
  onCancel: () => void;
}

export default function ConfirmDialog({
  open,
  title = "Xác nhận",
  message,
  onConfirm,
  onCancel,
}: ConfirmDialogProps) {
  if (!open) return null;

  return (
    <div className="confirm-overlay">
      <div className="confirm-modal">
        <h3>{title}</h3>
        <p>{message}</p>

        <div className="confirm-actions">
          <button className="btn-cancel" onClick={onCancel}>
            Huỷ
          </button>
          <button className="btn-confirm" onClick={onConfirm}>
            Xác nhận
          </button>
        </div>
      </div>
    </div>
  );
}
